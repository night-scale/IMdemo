package com.imdemo.handler;

import com.imdemo.EntailServer;
import com.imdemo.mapper.GroupMapper;
import com.imdemo.mapper.GroupMemberMapper;
import com.imdemo.model.Group;
import com.imdemo.model.GroupMember;
import com.imdemo.protocol.ProtocolFactory;

import com.imdemo.protocol.request.GroupCreateRequest;
import com.imdemo.protocol.request.GroupExitRequest;
import com.imdemo.protocol.response.GroupCreateResponse;
import com.imdemo.protocol.request.GroupJoinRequest;
import com.imdemo.protocol.response.GroupExitResponse;
import com.imdemo.protocol.response.GroupJoinResponse;

import com.imdemo.util.MyBatisUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.ibatis.session.SqlSession;
import com.imdemo.protocol.ResponseStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;


public class GroupChatHandler extends ChannelInboundHandlerAdapter {
    final ConcurrentMap<Long, com.imdemo.model.Group> groups = EntailServer.getGroups();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof GroupCreateRequest) {
            createGroup((GroupCreateRequest) msg, ctx.channel());
        } else if (msg instanceof GroupJoinRequest) {
            joinGroup((GroupJoinRequest) msg, ctx.channel());
        } else if (msg instanceof GroupExitRequest) {
            quitGroup((GroupExitRequest) msg, ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 创建群组
     */
    private void createGroup(GroupCreateRequest request, Channel channel) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            GroupMapper mapper = session.getMapper(GroupMapper.class);

            if (request.getMembers().size() == 1) {
                // 处理私聊逻辑
                GroupCreateResponse response = (GroupCreateResponse) ProtocolFactory.createMessage(
                        12,
                        (byte) 1,
                        request.getSequenceId(),
                        ResponseStatus.ILLEGAL_VALUE
                );
                channel.writeAndFlush(response);
            } else {
                List<GroupMember> groupMembers = new ArrayList<>();
                Group newGroup = new Group(request.getGroupName(), request.getUserId());

                // 插入群组到数据库
                mapper.insertGroup(newGroup);

                for (Integer id : request.getMembers()) {
                    GroupMember member = new GroupMember(newGroup.getId(), id, channel);
                    // 将成员添加到群组成员列表中
                    groupMembers.add(member);
                }

                // 将成员列表设置到群组中
                newGroup.setMembers(groupMembers);

                // 内存中创建群组
                groups.put(newGroup.getId(), newGroup);

                GroupCreateResponse response = (GroupCreateResponse) ProtocolFactory.createMessage(
                        12,
                        (byte) 1,
                        request.getSequenceId(),
                        ResponseStatus.SUCCESS
                );
                channel.writeAndFlush(response);
                session.commit();  // 提交数据库事务
            }
        } catch (Exception e) {
            GroupCreateResponse response = (GroupCreateResponse) ProtocolFactory.createMessage(
                    12,
                    (byte) 1,
                    request.getSequenceId(),
                    ResponseStatus.ERROR
            );
            channel.writeAndFlush(response);
        }
    }

    /**
     * 加入群组
     */
    private void joinGroup(GroupJoinRequest request, Channel channel)  {
        Group group = groups.get(request.getGroupId());


        if (group == null) {
            GroupJoinResponse response = (GroupJoinResponse) ProtocolFactory.createMessage(
12,
                    Byte.MAX_VALUE,
                    request.getSequenceId(),
                    ResponseStatus.NONEXISTENT
            );
            channel.writeAndFlush(response);
            return;
        }

        List<GroupMember> members = group.getMembers();
        boolean isAlreadyMember = members.stream()
                .anyMatch(member -> member.getMemberId().equals(request.getUserId()));

        if (isAlreadyMember) {
            GroupJoinResponse response = (GroupJoinResponse) ProtocolFactory.createMessage(
12,
                    Byte.MAX_VALUE,
                    request.getSequenceId(),
                    ResponseStatus.ALREADY_EXIST
            );
            channel.writeAndFlush(response);
        } else {
            try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
                GroupMemberMapper mapper = session.getMapper(GroupMemberMapper.class);
                GroupMember newMember = new GroupMember(request.getGroupId(), request.getUserId(), channel);

                // 插入数据库成员表
                mapper.insertGroupMember(newMember);
                session.commit(); // 提交数据库事务

                // 数据库插入成功后，再更新内存中的群组成员
                members.add(newMember);
                group.setMembers(members);

                GroupJoinResponse response = (GroupJoinResponse) ProtocolFactory.createMessage(
12,
                        Byte.MAX_VALUE,
                        request.getSequenceId(),
                        ResponseStatus.SUCCESS
                );
                channel.writeAndFlush(response);
            } catch (Exception e) {
                GroupJoinResponse response = (GroupJoinResponse) ProtocolFactory.createMessage(
12,
                        Byte.MAX_VALUE,
                        request.getSequenceId(),
                        ResponseStatus.ERROR
                );
                channel.writeAndFlush(response);
            }
        }
    }

    /**
     * 退出群组
     */
    private void quitGroup(GroupExitRequest request, Channel channel) {
        Group group = groups.get(request.getGroupId());

        // 检查群组是否存在
        if (group == null) {
            GroupExitResponse response = (GroupExitResponse) ProtocolFactory.createMessage(
12,
                    Byte.MAX_VALUE,
                    request.getSequenceId(),
                    ResponseStatus.NONEXISTENT
            );
            channel.writeAndFlush(response);
            return;
        }

        List<GroupMember> members = group.getMembers();

        // 检查当前用户是否在群组成员中
        GroupMember memberToRemove = members.stream()
                .filter(member -> member.getMemberId().equals(request.getUserId()))
                .findFirst()
                .orElse(null);

        if (memberToRemove == null) {
            GroupExitResponse response = (GroupExitResponse) ProtocolFactory.createMessage(
12,
                    Byte.MAX_VALUE,
                    request.getSequenceId(),
                    ResponseStatus.NO_SUCH_MEMBER
            );
            channel.writeAndFlush(response);
            return;
        }

        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            GroupMemberMapper mapper = session.getMapper(GroupMemberMapper.class);

            // 更新数据库，删除该用户在群组中的记录
            mapper.deleteGroupMember(request.getGroupId(), request.getUserId());
            session.commit(); // 提交事务

            // 数据库操作成功后再移除内存中的群组成员
            members.remove(memberToRemove);
            group.setMembers(members);

            GroupExitResponse response = (GroupExitResponse) ProtocolFactory.createMessage(
12,
                    Byte.MAX_VALUE,
                    request.getSequenceId(),
                    ResponseStatus.SUCCESS
            );
            channel.writeAndFlush(response);
        } catch (Exception e) {
            GroupExitResponse response = (GroupExitResponse) ProtocolFactory.createMessage(
12,
                    Byte.MAX_VALUE,
                    request.getSequenceId(),
                    ResponseStatus.ERROR
            );
            channel.writeAndFlush(response);
        }
    }

}




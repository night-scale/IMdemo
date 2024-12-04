package com.imdemo.handler;

import com.imdemo.model.Friendship;
import com.imdemo.EntailServer;
import com.imdemo.protocol.ProtocolFactory;
import com.imdemo.protocol.ResponseStatus;
import com.imdemo.util.MyBatisUtil;
import com.imdemo.model.PrivateChat;
import com.imdemo.protocol.request.PrivateChatCreateRequest;
import com.imdemo.protocol.response.PrivateChatCreateResponse;
import com.imdemo.mapper.FriendshipMapper;
import com.imdemo.mapper.PrivateChatMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.ConcurrentMap;


public class PrivateChatHandler extends ChannelInboundHandlerAdapter {
    // 获取全局的私聊映射
    final ConcurrentMap<Integer, Integer> privateChats = EntailServer.getPrivateChats();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof PrivateChatCreateRequest request) {
            // 创建一个 MyBatis 会话
            try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
                PrivateChatMapper privateChatMapper = session.getMapper(PrivateChatMapper.class);
                FriendshipMapper friendshipMapper = session.getMapper(FriendshipMapper.class);

                // 检查是否已经有私聊会话存在
                if (privateChats.containsKey(request.getMember1Id()) || privateChats.containsKey(request.getMember2Id())) {
                    PrivateChatCreateResponse response = (PrivateChatCreateResponse) ProtocolFactory.createMessage(
                        12,
                            (byte) 1,
                            request.getSequenceId()
                    );
                    ctx.writeAndFlush(response);
                    return;
                }

                // 检查是否存在好友关系
                Friendship friendship = friendshipMapper.getFriendshipStatus(request.getMember1Id(), request.getMember2Id());

                // 创建新的私聊会话
                PrivateChat privateChat = new PrivateChat();
                privateChat.setMember1(request.getMember1Id());
                privateChat.setMember2(request.getMember2Id());
                // 如果两者不是好友，设置为临时私聊
                privateChat.setTemporary(friendship == null);

                // 插入新的私聊记录
                privateChatMapper.insertPrivateChat(privateChat);

                // 将新的私聊会话存储到内存中（缓存）
                privateChats.put(request.getMember1Id(), request.getMember2Id());
                privateChats.put(request.getMember2Id(), request.getMember1Id());

                // 设置成功响应
                PrivateChatCreateResponse response = (PrivateChatCreateResponse) ProtocolFactory.createMessage(
                        12,
                        (byte) 1,
                        request.getSequenceId(),
                        ResponseStatus.SUCCESS
                );

                // 提交事务
                session.commit();

                // 将响应发送给客户端
                ctx.writeAndFlush(response);
            } catch (Exception e) {
                // 捕获并处理异常
                e.printStackTrace();
                PrivateChatCreateResponse response = (PrivateChatCreateResponse) ProtocolFactory.createMessage(
                        12,
                        (byte) 1,
                        request.getSequenceId(),
                        ResponseStatus.ERROR
                );
                ctx.writeAndFlush(response);
            }
        }
    }

}

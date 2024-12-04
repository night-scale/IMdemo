package com.imdemo.handler;

import com.imdemo.EntailServer;
import com.imdemo.mapper.MessageMapper;
import com.imdemo.mapper.OfflineMessageMapper;
import com.imdemo.model.*;
import com.imdemo.protocol.ProtocolFactory;
import com.imdemo.protocol.ResponseStatus;
import com.imdemo.protocol.request.MessageSendRequest;
import com.imdemo.protocol.response.MessageSendResponse;
import com.imdemo.protocol.response.MessageReceive;
import com.imdemo.util.MyBatisUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.ibatis.session.SqlSession;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class SendMessageHandler extends ChannelInboundHandlerAdapter {
    private static final ConcurrentMap<String, ChannelHandlerContext> clients = EntailServer.getClients();
    private static final ConcurrentMap<Long, Group> groups = EntailServer.getGroups();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof MessageSendRequest request){
            Message message = new Message(
                    new Timestamp(System.currentTimeMillis()),
                    request.getSenderId(),
                    request.getReceiverId(),
                    request.getContent(),
                    Source.fromValue(request.getSource()),  // 可能抛出异常
                    MessageType.fromValue(request.getType()), // 可能抛出异常
                    request.getGroupId()
            );
            if (Source.PRIVATE.equals(Source.fromValue(request.getSource()))) {
                sendPrivateMessage(ctx, message, request.getSequenceId());
            } else if (Source.GROUP.equals(Source.fromValue(request.getSource()))) {
                sendGroupMessage(ctx, message, request.getSequenceId());
            }

            try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
                MessageMapper mapper = session.getMapper(MessageMapper.class);
                mapper.insertMessage(message);
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    private void sendPrivateMessage(ChannelHandlerContext ctx, Message msg, long sequenceId) {
        String senderId = msg.getSenderId().toString();
        ChannelHandlerContext targetCtx = clients.get(senderId);

        // 创建发送方的回复消息，表示操作成功
        MessageSendResponse response = (MessageSendResponse) ProtocolFactory.createMessage(
                        12,
                Byte.MAX_VALUE,
                sequenceId,
                ResponseStatus.SUCCESS
        );
        try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
            OfflineMessageMapper mapper = session.getMapper(OfflineMessageMapper.class);
            if (targetCtx == null) {
                // 目标用户不在线，将消息存入离线消息
                OfflineMessage offlineMessage = new OfflineMessage(
                        new Timestamp(System.currentTimeMillis()),
                        msg.getSenderId(),
                        msg.getReceiverId(),
                        msg.getContent(),
                        msg.getType() // 假设 MessageType 定义了消息类型
                );
                mapper.insertOfflineMessage(offlineMessage); // 插入到离线消息表

                ctx.writeAndFlush(response);
            } else {
                // 目标用户在线，直接发送消息
                MessageReceive receive = (MessageReceive) ProtocolFactory.createMessage(
                        12,
                        Byte.MAX_VALUE,
                        sequenceId,
                        ResponseStatus.SUCCESS
                );
                receive.setSenderId(msg.getSenderId());
                receive.setMsg(msg.getContent());
                receive.setGroupId(0);

                targetCtx.writeAndFlush(receive); // 发送消息给目标用户

                ctx.writeAndFlush(response);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendGroupMessage(ChannelHandlerContext ctx, Message msg, long sequenceId) {
        Group group = groups.get(msg.getGroupId());

        if (group == null) {
            MessageSendResponse response = (MessageSendResponse) ProtocolFactory.createMessage(
                        12,
                    Byte.MAX_VALUE,
                    sequenceId,
                    ResponseStatus.NONEXISTENT
            );
            ctx.writeAndFlush(response);
            return;
        }
        List<GroupMember> members = group.getMembers();

        for (GroupMember gm : members) {
            MessageReceive receive = (MessageReceive) ProtocolFactory.createMessage(
                        12,
                    Byte.MAX_VALUE,
                    sequenceId,//待定
                    ResponseStatus.SUCCESS
            );
            receive.setSenderId(msg.getSenderId());
            receive.setMsg(msg.getContent());
            receive.setGroupId(msg.getGroupId());
            gm.getChannel().writeAndFlush(receive);
        }

        MessageSendResponse response = (MessageSendResponse) ProtocolFactory.createMessage(
                        12,
                Byte.MAX_VALUE,
                sequenceId,
                ResponseStatus.SUCCESS
        );
        ctx.writeAndFlush(response);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

package com.imdemo.handler;

import com.imdemo.EntailServer;
import com.imdemo.model.User;
import com.imdemo.protocol.ProtocolFactory;
import com.imdemo.protocol.ResponseStatus;
import com.imdemo.util.MyBatisUtil;

import com.imdemo.protocol.request.LoginRequest;
import com.imdemo.protocol.response.LoginResponse;

import com.imdemo.mapper.UserMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.ConcurrentMap;

public class LoginHandler extends SimpleChannelInboundHandler<LoginRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest msg) {
        System.out.println("[LoginHandler] executed");
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.getUserById(msg.getId());

            if (user == null) {
                System.out.println("[LoginHandler]  NONEXISTENT");

                LoginResponse response = (LoginResponse) ProtocolFactory.createMessage(
                    12,
                        (byte) -2,
                        msg.getSequenceId(),
                        ResponseStatus.NONEXISTENT
                );
                ctx.writeAndFlush(response);

                return;
            }

            if (!user.getPassword().equals(msg.getPassword())) {
                System.out.println("[LoginHandler] failed! create response...");

                LoginResponse response = (LoginResponse) ProtocolFactory.createMessage(
                    12,
                        (byte) -2,
                        msg.getSequenceId(),
                        ResponseStatus.AUTHENTICATION_FAILED
                );
                ctx.writeAndFlush(response);
                System.out.println("[LoginHandler] send response...");

                return;
            }
        } catch (Exception e) {
            System.out.println("[LoginHandler] ERROR");
            System.out.println(e.toString());
            LoginResponse response = (LoginResponse) ProtocolFactory.createMessage(
                    14,
                    (byte) -2,
                    msg.getSequenceId(),
                    ResponseStatus.ERROR
            );
            ctx.writeAndFlush(response);
            e.printStackTrace();
            System.out.println("[LoginHandler] ERROR return");
            return;
        }

        String userId = Integer.toString(msg.getId());
        ConcurrentMap<String, ChannelHandlerContext> clients = EntailServer.getClients();
        ChannelHandlerContext existingCtx = clients.get(userId);

        if (existingCtx == null) {
            clients.put(userId, ctx);
            ctx.channel().attr(AttributeKey.valueOf("userId")).set(msg.getId());
            System.out.println("[LoginHandler] SUCCESS");

            LoginResponse response = (LoginResponse) ProtocolFactory.createMessage(
                    12,
                    (byte) -2,
                    msg.getSequenceId(),
                    ResponseStatus.SUCCESS
            );
            ctx.writeAndFlush(response);
        }
//            else {
//                System.out.println("[LoginHandler] create response");
//
//                LoginResponse response = (LoginResponse) ProtocolFactory.createMessage(
//                        12,
//                        (byte) -2,
//                        request.getSequenceId(),
//                        ResponseStatus.NONEXISTENT
//                );
//
//                ctx.writeAndFlush(response);
//            }

    }
}


package com.imdemo.handler;

import com.imdemo.protocol.ResponseStatus;
import com.imdemo.model.User;
import com.imdemo.protocol.ProtocolFactory;
import com.imdemo.protocol.request.SignupRequest;
import com.imdemo.protocol.response.SignupResponse;
import com.imdemo.util.MyBatisUtil;
import com.imdemo.mapper.UserMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.ibatis.session.SqlSession;

public class SignupHandler extends SimpleChannelInboundHandler<SignupRequest> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, SignupRequest request) throws Exception {
            System.out.println("[SignupHandler] executed");

            // 1. 进行数据格式验证
            if (!isValidEmail(request.getEmail())) {
                SignupResponse response = (SignupResponse) ProtocolFactory.createMessage(
                        12,
                        (byte)1,
                        request.getSequenceId(),
                        ResponseStatus.STRING_FORMAT_ERROR
                );
                ctx.writeAndFlush(response);
                return; // 结束处理
            }
            // 2. 进行数据库验证
            try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
                UserMapper mapper = session.getMapper(UserMapper.class);

                // 检查邮箱是否已经注册
                if (mapper.getUserByEmail(request.getEmail()) != null) {
                    SignupResponse response = (SignupResponse) ProtocolFactory.createMessage(
                        12,
                            (byte)1,
                            request.getSequenceId(),
                            ResponseStatus.ALREADY_REGISTERED
                    );
                    ctx.writeAndFlush(response);
                    return;
                }else{
                    User newUser = new User();
                    newUser.setNickname(request.getNickname());
                    newUser.setPassword(request.getPassword());
                    newUser.setEmail(request.getEmail());
                    newUser.setPhoneNumber(request.getPhoneNumber());

                    mapper.insertUser(newUser);
                    session.commit();
                }
            }
    }
    private boolean isValidEmail(String email) {
        // 简单的邮箱验证逻辑
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

}
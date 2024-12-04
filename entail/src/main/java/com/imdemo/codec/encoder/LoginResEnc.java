package com.imdemo.codec.encoder;

import com.imdemo.codec.annotation.MessageEncoder;
import com.imdemo.codec.interfaces.Encoder;
import com.imdemo.protocol.response.LoginResponse;
import io.netty.buffer.ByteBuf;

@MessageEncoder(type = -2)
public class LoginResEnc implements Encoder<LoginResponse> {
    @Override
    public void encode(ByteBuf out, LoginResponse message) throws Exception {
        out.writeInt(message.getLength());
        out.writeByte(-2);

        out.writeLong(message.getSequenceId());

        out.writeByte(message.getStatus());
        System.out.println("[response -2] log in");
    }
}

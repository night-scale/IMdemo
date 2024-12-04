package com.imdemo.codec.decoder;


import com.imdemo.codec.interfaces.Decoder;
import io.netty.buffer.ByteBuf;
import com.imdemo.codec.annotation.MessageDecoder;
import com.imdemo.protocol.request.LoginRequest;

@MessageDecoder(type = 0x02)
public class LoginReqDec implements Decoder<LoginRequest> {

    @Override
    public void decode(ByteBuf in, LoginRequest message) throws Exception {
        message.setId(in.readInt());
        message.setPassword(readString(in));
    }
}

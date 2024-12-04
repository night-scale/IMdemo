package com.imdemo.codec.decoder;

import com.imdemo.codec.annotation.ProtocolData;
import com.imdemo.codec.interfaces.Decoder;
import com.imdemo.protocol.request.SignupRequest;
import io.netty.buffer.ByteBuf;

//@ProtocolData(order = 1, dataType = String.class)
//private String nickname;
//
//@ProtocolData(order = 2, dataType = String.class)
//private String password;
//
//@ProtocolData(order = 3, dataType = String.class)
//private String email;
//
//@ProtocolData(order = 4, dataType = String.class)
//private String phoneNumber;
public class SignupReqDec implements Decoder<SignupRequest> {
    @Override
    public void decode(ByteBuf in, SignupRequest message) throws Exception {
        message.setNickname(readString(in));
        message.setPassword(readString(in));
        message.setEmail(readString(in));
        message.setPhoneNumber(readString(in));
    }
}

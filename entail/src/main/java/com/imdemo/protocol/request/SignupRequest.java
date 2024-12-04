package com.imdemo.protocol.request;

import com.imdemo.codec.annotation.DecoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProtocolMessage(type = 0x01, description = "Sign up request")
@DecoderGenerate(type = 0x01)
public class SignupRequest extends ProtocolBase {

    @ProtocolData(order = 1, dataType = String.class)
    private String nickname;

    @ProtocolData(order = 2, dataType = String.class)
    private String password;

    @ProtocolData(order = 3, dataType = String.class)
    private String email;

    @ProtocolData(order = 4, dataType = String.class)
    private String phoneNumber;

    public SignupRequest(int length, long sequenceId, byte status) {
        super(length, (byte) 0x01, sequenceId);
    }

    @Override
    public void process() {
        // Implement the logic for processing a sign-up request
    }
}

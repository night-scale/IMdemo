package com.imdemo.protocol.response;

import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import com.imdemo.codec.annotation.EncoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ProtocolMessage(type = (byte) -2, description = "log in response")
@EncoderGenerate(type = (byte) -2)
public class LoginResponse extends ProtocolBase {

    @ProtocolData(order = 1, dataType = byte.class)
    private byte status;

    public LoginResponse(int length, long sequenceId, byte status) {
        super(length, (byte) -2, sequenceId);
        this.status = status;
    }

    @Override
    public void process() {
        // Implement the logic for processing login response
    }
}

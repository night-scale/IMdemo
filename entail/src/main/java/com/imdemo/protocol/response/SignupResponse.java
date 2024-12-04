package com.imdemo.protocol.response;

import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import com.imdemo.codec.annotation.EncoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ProtocolMessage(type = (byte) -1, description = "sign up response")
@EncoderGenerate(type = (byte) -1)
public class SignupResponse extends ProtocolBase {

    @ProtocolData(order = 1, dataType = byte.class)
    private byte status;

    public SignupResponse(int length, long sequenceId, byte status) {
        super(length, (byte) -1, sequenceId);
        this.status = status;
    }

    @Override
    public void process() {
        // Implement the logic for processing sign up response
    }
}

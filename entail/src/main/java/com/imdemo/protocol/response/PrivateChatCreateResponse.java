package com.imdemo.protocol.response;

import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import com.imdemo.codec.annotation.EncoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProtocolMessage(type = (byte) -3, description = "private chat create response")
@EncoderGenerate(type = (byte) -3)
public class PrivateChatCreateResponse extends ProtocolBase {

    @ProtocolData(order = 1, dataType = byte.class)
    private byte status;

    public PrivateChatCreateResponse(int length, long sequenceId, byte status) {
        super(length, (byte) -3, sequenceId);
        this.status = status;
    }

    @Override
    public void process() {
        // Implement the logic for processing private chat create response
    }
}

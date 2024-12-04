package com.imdemo.protocol.response;

import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import com.imdemo.codec.annotation.EncoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import lombok.Getter;
import lombok.Setter;

@ProtocolMessage(type = (byte) -7, description = "send message response")
@EncoderGenerate(type = (byte) -7)
@Getter
@Setter
public class MessageSendResponse extends ProtocolBase {

    @ProtocolData(order = 1, dataType = byte.class)
    private byte status;

    public MessageSendResponse(int length, long sequenceId, byte status) {
        super(length, (byte) -7, sequenceId);
        this.status = status;
    }

    @Override
    public void process() {
        // Implement the logic for processing send message response
    }
}

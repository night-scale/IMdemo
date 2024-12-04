package com.imdemo.protocol.response;

import com.imdemo.codec.annotation.EncoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProtocolMessage(type = (byte) -4, description = "group create response")
@EncoderGenerate(type = (byte) -4)
public class GroupCreateResponse extends ProtocolBase {

    @ProtocolData(order = 1, dataType = byte.class)
    private byte status;

    public GroupCreateResponse(int length, long sequenceId, byte status) {
        super(length, (byte) -4, sequenceId);
        this.status = status;
    }

    @Override
    public void process() {
        // Implement the logic for processing a group creation response
    }
}

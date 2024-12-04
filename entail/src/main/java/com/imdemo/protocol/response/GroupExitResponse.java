package com.imdemo.protocol.response;

import com.imdemo.codec.annotation.EncoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProtocolMessage(type = (byte) -6, description = "exit group response")
@EncoderGenerate(type = (byte) -6)
public class GroupExitResponse extends ProtocolBase {

    @ProtocolData(order = 1, dataType = byte.class)
    private byte status;

    public GroupExitResponse(int length, long sequenceId, byte status) {
        super(length, (byte) -6, sequenceId);
        this.status = status;
    }

    @Override
    public void process() {
        // Implement the logic for processing a group exit response
    }
}

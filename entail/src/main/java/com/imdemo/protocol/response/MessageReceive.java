package com.imdemo.protocol.response;

import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import com.imdemo.codec.annotation.EncoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProtocolMessage(type = (byte) -8, description = "message receive response")
@EncoderGenerate(type = (byte) -8)
public class MessageReceive extends ProtocolBase {

    @ProtocolData(order = 1, dataType = int.class)
    private Integer senderId;

    @ProtocolData(order = 2, dataType = String.class)
    private String msg;

    @ProtocolData(order = 3, dataType = long.class)
    private long groupId;

    public MessageReceive(int length, long sequenceId, byte status) {
        super(length, (byte) -8, sequenceId);
    }

    @Override
    public void process() {
        // Implement the logic for processing message receive
    }
}

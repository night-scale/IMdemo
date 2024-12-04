package com.imdemo.protocol.request;

import com.imdemo.codec.annotation.DecoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProtocolMessage(type = 0x06, description = "exit group request")
@DecoderGenerate(type = 0x06)
public class GroupExitRequest extends ProtocolBase {
    @ProtocolData(order = 1, dataType = long.class)
    private long groupId;
    @ProtocolData(order = 2, dataType = int.class)
    private int userId;

    public GroupExitRequest(int length, long sequenceId, byte status) {
        super(length, (byte)0x06, sequenceId);
    }

    @Override
    public void process() {

    }
}

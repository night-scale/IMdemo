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
@ProtocolMessage(type = 0x05, description = "join group request")
@DecoderGenerate(type = 0x05)
public class GroupJoinRequest extends ProtocolBase {
    @ProtocolData(order = 1, dataType = int.class)
    private Integer userId;
    @ProtocolData(order = 2, dataType = long.class)
    private long groupId;

    public GroupJoinRequest(int length, long sequenceId, byte status) {
        super(length, (byte)0x05, sequenceId);
    }

    @Override
    public void process() {

    }
}

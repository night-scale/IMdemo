package com.imdemo.protocol.request;


import com.imdemo.codec.annotation.DecoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ProtocolMessage(type = 0x04, description = "group create request")
@DecoderGenerate(type = 0x04)
public class GroupCreateRequest extends ProtocolBase {
    @ProtocolData(order = 1, dataType = String.class)
    private String groupName;
    @ProtocolData(order = 2, dataType = List.class)
    private List<Integer> members;
    @ProtocolData(order = 3, dataType = int.class)
    private Integer userId;

    public GroupCreateRequest(int length, long sequenceId, byte status) {
        super(length, (byte) 0x04, sequenceId);
//        this.groupName = data.get

    }

    @Override
    public void process() {

    }
}

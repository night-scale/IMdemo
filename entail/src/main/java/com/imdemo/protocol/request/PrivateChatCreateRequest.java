package com.imdemo.protocol.request;

import com.imdemo.codec.annotation.DecoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProtocolMessage(type = 0x03, description = "request for creating private chat between two persons")
@DecoderGenerate(type = 0x03)
public class PrivateChatCreateRequest extends ProtocolBase {

    @ProtocolData(order = 1, dataType = int.class)
    private Integer member1Id;

    @ProtocolData(order = 2, dataType = int.class)
    private Integer member2Id;

    public PrivateChatCreateRequest(int length, long sequenceId, byte status) {
        super(length, (byte) 0x03, sequenceId);
    }

    @Override
    public void process() {
        // Implement the logic for processing a private chat creation request
    }
}

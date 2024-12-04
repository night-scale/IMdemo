package com.imdemo.protocol.request;

import com.imdemo.codec.annotation.DecoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import com.imdemo.model.MessageType;
import com.imdemo.model.Source;
import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProtocolMessage(type = 0x07, description = "send message request")
@DecoderGenerate(type = 0x07)
public class MessageSendRequest extends ProtocolBase {
    @ProtocolData(order = 1, dataType = int.class)
    private int senderId;
    @ProtocolData(order = 2, dataType = int.class)
    private int receiverId;
    @ProtocolData(order = 3, dataType = String.class)
    private String content;
    @ProtocolData(order = 4, dataType = byte.class)
    private byte source;
    @ProtocolData(order = 5, dataType = byte.class)
    private byte type;
    @ProtocolData(order = 6, dataType = long.class)
    private long groupId;

    public MessageSendRequest(int length, long sequenceId, byte status) {
        super(length, (byte) 0x07, sequenceId);
    }

    public Source getSourceEnum() {
        return Source.fromValue(source);
    }

    public MessageType getTypeEnum() {
        return MessageType.fromValue(type);
    }

    @Override
    public void process() {

    }
}


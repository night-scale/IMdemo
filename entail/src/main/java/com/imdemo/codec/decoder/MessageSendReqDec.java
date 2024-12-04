package com.imdemo.codec.decoder;

import com.imdemo.codec.interfaces.Decoder;
import io.netty.buffer.ByteBuf;
import com.imdemo.codec.annotation.MessageDecoder;
import com.imdemo.protocol.request.MessageSendRequest;

@MessageDecoder(type = 0x07)
public class MessageSendReqDec implements Decoder<MessageSendRequest> {

    @Override
    public void decode(ByteBuf in, MessageSendRequest message) throws Exception {
        message.setSenderId(in.readInt());
        message.setReceiverId(in.readInt());
        message.setContent(readString(in));
        message.setSource(in.readByte());
        message.setType(in.readByte());
        message.setGroupId(in.readLong());
    }
}

package com.imdemo.codec.decoder;


import com.imdemo.codec.interfaces.Decoder;
import io.netty.buffer.ByteBuf;
import com.imdemo.codec.annotation.MessageDecoder;
import com.imdemo.protocol.request.GroupExitRequest;

@MessageDecoder(type = 0x06)
public class GroupExitReqDec implements Decoder<GroupExitRequest> {

    @Override
    public void decode(ByteBuf in, GroupExitRequest message) throws Exception {
        message.setGroupId(in.readLong());
        message.setUserId(in.readInt());
    }
}

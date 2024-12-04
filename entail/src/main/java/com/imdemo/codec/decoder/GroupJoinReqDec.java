package com.imdemo.codec.decoder;

import com.imdemo.codec.interfaces.Decoder;
import io.netty.buffer.ByteBuf;
import com.imdemo.codec.annotation.MessageDecoder;
import com.imdemo.protocol.request.GroupJoinRequest;

@MessageDecoder(type = 0x05)
public class GroupJoinReqDec implements Decoder<GroupJoinRequest> {

    @Override
    public void decode(ByteBuf in, GroupJoinRequest message) throws Exception {
        message.setUserId(in.readInt());
        message.setGroupId(in.readLong());
    }
}

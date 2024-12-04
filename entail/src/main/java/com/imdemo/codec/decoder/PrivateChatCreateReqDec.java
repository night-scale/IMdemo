package com.imdemo.codec.decoder;


import com.imdemo.codec.interfaces.Decoder;
import com.imdemo.protocol.request.PrivateChatCreateRequest;
import io.netty.buffer.ByteBuf;
import com.imdemo.codec.annotation.MessageDecoder;

@MessageDecoder(type = 0x03)
public class PrivateChatCreateReqDec implements Decoder<PrivateChatCreateRequest> {

    @Override
    public void decode(ByteBuf in, PrivateChatCreateRequest message) throws Exception {
        // Decode member1Id (int)
        message.setMember1Id(in.readInt());

        // Decode member2Id (int)
        message.setMember2Id(in.readInt());
    }
}

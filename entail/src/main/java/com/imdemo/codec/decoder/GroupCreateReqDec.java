package com.imdemo.codec.decoder;


import com.imdemo.codec.interfaces.Decoder;
import com.imdemo.protocol.request.GroupCreateRequest;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;
import com.imdemo.codec.annotation.MessageDecoder;

@MessageDecoder(type = 0x04)
public class GroupCreateReqDec implements Decoder<GroupCreateRequest> {

    @Override
    public void decode(ByteBuf in, GroupCreateRequest message) throws Exception {
        // Decode groupName (String)
        message.setGroupName(readString(in));

        // Decode members (List<Integer>)
        int membersCount = in.readInt(); // Assume count of list elements is included
        List<Integer> members = new ArrayList<>();
        for (int i = 0; i < membersCount; i++) {
            members.add(in.readInt());
        }
        message.setMembers(members);

        // Decode userId (int)
        message.setUserId(in.readInt());
    }
}

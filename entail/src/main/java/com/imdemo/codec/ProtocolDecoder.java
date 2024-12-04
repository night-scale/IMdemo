package com.imdemo.codec;

import com.imdemo.codec.interfaces.Decoder;
import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 9) {
            return;
        }
        int length = in.readInt();
        byte method = in.readByte();
        long sequenceId = in.readLong();
        ProtocolBase message = ProtocolFactory.createMessage(length, method, sequenceId);

        System.out.println("[CodecManager.getDecoder] method = " + method);
        Decoder<? extends ProtocolBase> decoder = CodecManager.getDecoder(method);
        if (decoder != null && message != null) {
            decodeMessage(decoder, in, message);
            System.out.println("[ProtocolDecoder][decode] pass " + message +" to handler");
            out.add(message);
        } else {
            System.err.println("No decoder found for message type: " + method + ", sequenceId: " + sequenceId);
            CodecManager.printAll();
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends ProtocolBase> void decodeMessage(Decoder<T> decoder, ByteBuf in, ProtocolBase message) throws Exception {
        decoder.decode(in, (T) message);
    }
}

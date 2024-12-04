package com.imdemo.codec.interfaces;

import com.imdemo.protocol.ProtocolBase;
import com.imdemo.util.CodecUtil;
import io.netty.buffer.ByteBuf;

// 解码器接口
public interface Decoder<T extends ProtocolBase> {
    void decode(ByteBuf in, T message) throws Exception;
    default String readString(ByteBuf in) {
        return CodecUtil.readString(in);
    }
}


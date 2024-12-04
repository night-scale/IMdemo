package com.imdemo.codec.interfaces;

import com.imdemo.protocol.ProtocolBase;
import io.netty.buffer.ByteBuf;

// 解码器接口
public interface Encoder<T extends ProtocolBase> {
    void encode(ByteBuf out, T message) throws Exception;
}

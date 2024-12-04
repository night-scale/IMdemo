package com.imdemo.util;

import io.netty.buffer.ByteBuf;

public class CodecUtil {
    public static String readString(ByteBuf in){
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        return new String(bytes);
    }
}

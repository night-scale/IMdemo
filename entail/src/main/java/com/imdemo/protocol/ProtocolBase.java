package com.imdemo.protocol;

import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class ProtocolBase implements Serializable {
    private final int length;
    private final byte method;  // 消息类型
    private final long sequenceId;   // 序列号

    public ProtocolBase(int length, byte method, long sequenceId) {
        this.length = length;
        this.method = method;
        this.sequenceId = sequenceId;
    }
    public abstract void process();
}
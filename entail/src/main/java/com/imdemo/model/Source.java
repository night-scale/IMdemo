package com.imdemo.model;

import lombok.Getter;

@Getter
public enum Source {
    PRIVATE((byte) 1),
    GROUP((byte) 2);

    private final byte value;

    Source(byte value) {
        this.value = value;
    }

    // 通过数值获取枚举
    public static Source fromValue(byte value) {
        for (Source source : Source.values()) {
            if (source.value == value) {
                return source;
            }
        }
        throw new IllegalArgumentException("Unknown source value: " + value);
    }
}


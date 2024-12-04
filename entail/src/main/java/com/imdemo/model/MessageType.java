package com.imdemo.model;

import lombok.Getter;

@Getter
public enum MessageType {
    TEXT((byte) 1),
    IMAGE((byte) 2),
    AUDIO((byte) 3),
    VIDEO((byte) 4),
    FILE((byte) 5);

    private final byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public static MessageType fromValue(byte value) {
        for (MessageType type : MessageType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown message type value: " + value);
    }
}




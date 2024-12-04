package com.imdemo.model;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
public class Message {
    private Long id;
    private Timestamp timestamp;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private Source source;   // 'PRIVATE' or 'GROUP'，可以直接使用 Source 枚举
    private MessageType type; // 'TEXT', 'IMAGE', 'AUDIO', 'VIDEO', 'FILE'，可以直接使用枚举
    private Long groupId;     // Nullable, only used for group messages

    // 构造方法调整，将枚举转化为 byte
    public Message(Timestamp timestamp, Integer senderId, Integer receiverId, String content, Source source, MessageType type, Long groupId) {
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.source = source;
        this.type = type;
        this.groupId = groupId;
    }
}



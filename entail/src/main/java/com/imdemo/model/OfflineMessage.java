package com.imdemo.model;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class OfflineMessage {
    // Getters and Setters
    private Timestamp timestamp;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private MessageType type;

    public OfflineMessage(Timestamp timestamp, Integer senderId, Integer receiverId, String content, MessageType type) {
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.type = type;
    }
}


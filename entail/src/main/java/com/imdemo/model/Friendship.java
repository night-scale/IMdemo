package com.imdemo.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Friendship {
    private int userId;
    private int friendId;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

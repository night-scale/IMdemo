package com.imdemo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

public class User {

    private int id;
    @Setter
    @Getter
    private String nickname;
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    private String phoneNumber;
    @Getter
    @Setter
    private String email;
    @Setter
    @Getter
    private String userPortrait;

    @Setter
    @Getter
    private Instant createdAt;
    @Setter
    @Getter
    private Instant updatedAt;


}
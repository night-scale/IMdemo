package com.imdemo.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PrivateChat {

    private Long id;
    private Timestamp startTime;
    private boolean temporary;
    private int member1;
    private int member2;
}


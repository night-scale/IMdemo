package com.imdemo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Data
public class Group {
    private Long id;                  // 自增 ID
    private String name;              // 群名称

    public Group(String name, Integer hostId) {
        this.name = name;
        this.hostId = Long.valueOf(hostId);
    }

    private Long hostId;              // 群主 ID

    @Setter
    @Getter
    private Instant startTime;        // 群开始时间，数据库的 `start_time`

    @Setter
    @Getter
    private Instant disbandTime;      // 解散时间

    @Getter
    private List<GroupMember> members;  // 群成员列表
    @Setter
    @Getter
    private List<Long> messageId;     // 消息 ID 列表

    @Getter
    @Setter
    private boolean chatted;          // 是否已聊天

    @Getter
    @Setter
    private String groupStatus;       // 群状态
}

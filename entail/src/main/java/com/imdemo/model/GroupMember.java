package com.imdemo.model;

import io.netty.channel.Channel;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class GroupMember {
    private Long groupId;  // 对应数据库中的 group_id
    private Integer memberId;  // 对应数据库中的 member_id

    private Timestamp joinedAt;  // 对应数据库中的 joined_at
    private Timestamp leftAt;  // 对应数据库中的 left_at (可以为空)
    private String maskNickname;  // 对应数据库中的 mask_nickname

    private Channel channel;

    public GroupMember(Long groupId, Integer memberId, Channel channel) {
        this.groupId = groupId;
        this.memberId = memberId;
//        this.maskNickname = maskNickname;
        this.channel = channel;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMember that = (GroupMember) o;
        return Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}

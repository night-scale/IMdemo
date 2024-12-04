package com.imdemo.mapper;

import com.imdemo.model.GroupMember;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface GroupMemberMapper {
    @Insert("INSERT INTO Group_Member (group_id, member_id, joined_at, mask_nickname) VALUES (#{groupId}, #{memberId}, #{joinedAt}, #{maskNickname})")
    void insertGroupMember(GroupMember groupMember);

    @Delete("DELETE FROM Group_Member WHERE group_id = #{groupId} AND member_id = #{userId}")
    void deleteGroupMember(Long groupId, Integer userId);

}


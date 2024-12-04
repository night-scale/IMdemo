package com.imdemo.mapper;

import com.imdemo.model.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GroupMapper {

    @Insert("INSERT INTO Group_table (group_name, host_id) VALUES (#{name}, #{hostId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertGroup(Group group);

    @Insert({
            "<script>",
            "INSERT INTO Group_Member (group_id, member_id) VALUES ",
            "<foreach collection='memberId' item='member' separator=','>",
            "(#{group.id}, #{member})",
            "</foreach>",
            "</script>"
    })
    void insertGroupMembers(@Param("group") Group group);

    @Insert({
            "<script>",
            "INSERT INTO Group_Message (group_id, message_id) VALUES ",
            "<foreach collection='messageId' item='message' separator=','>",
            "(#{group.id}, #{message})",
            "</foreach>",
            "</script>"
    })
    void insertGroupMessages(@Param("group") Group group);

    @Select("SELECT id, start_time FROM Group_table WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "memberId", column = "id",
                    many = @Many(select = "getMembersByGroupId")),
            @Result(property = "messageId", column = "id",
                    many = @Many(select = "getMessagesByGroupId"))
    })
    Group getGroupById(Long id);

    @Select("SELECT member_id FROM Group_Member WHERE group_id = #{groupId}")
    List<Integer> getMembersByGroupId(Long groupId);

    @Select("SELECT message_id FROM Group_Message WHERE group_id = #{groupId}")
    List<Long> getMessagesByGroupId(Long groupId);

    @Delete("DELETE FROM Group_table WHERE id = #{id}")
    void deleteGroupById(Long id);

    @Delete("DELETE FROM Group_Member WHERE group_id = #{id}")
    void deleteGroupMembers(Long id);

    @Delete("DELETE FROM Group_Message WHERE group_id = #{id}")
    void deleteGroupMessages(Long id);
}

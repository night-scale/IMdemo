package com.imdemo.mapper;
import com.imdemo.model.PrivateChat;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PrivateChatMapper {

    // 插入新的私聊记录
    @Insert("INSERT INTO private_chat (temporary, member1, member2) " +
            "VALUES (#{temporary}, #{member1}, #{member2})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPrivateChat(PrivateChat privateChat);

    // 根据私聊 ID 获取私聊记录
    @Select("SELECT * FROM private_chat WHERE id = #{id}")
    PrivateChat getPrivateChatById(Long id);

    // 根据两个成员的 ID 获取他们之间的私聊记录
    @Select("SELECT * FROM private_chat WHERE " +
            "(LEAST(member1, member2) = LEAST(#{member1}, #{member2}) " +
            "AND GREATEST(member1, member2) = GREATEST(#{member1}, #{member2}))")
    PrivateChat getPrivateChatByMembers(@Param("member1") int member1, @Param("member2") int member2);

    // 获取某个用户参与的所有私聊
    @Select("SELECT * FROM private_chat WHERE member1 = #{userId} OR member2 = #{userId}")
    List<PrivateChat> getPrivateChatsByUserId(int userId);

    // 删除指定 ID 的私聊记录
    @Delete("DELETE FROM private_chat WHERE id = #{id}")
    int deletePrivateChat(Long id);

    // 更新某个私聊记录的临时状态
    @Update("UPDATE private_chat SET temporary = #{temporary} WHERE id = #{id}")
    int updatePrivateChatTemporaryStatus(@Param("id") Long id, @Param("temporary") boolean temporary);
}


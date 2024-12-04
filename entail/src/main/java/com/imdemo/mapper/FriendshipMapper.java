package com.imdemo.mapper;

import com.imdemo.model.Friendship;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface FriendshipMapper {

    // 添加好友关系，默认状态为 PENDING
    @Insert("INSERT INTO Friendship (user_id, friend_id, status) " +
            "VALUES (#{userId}, #{friendId}, 'PENDING')")
    int sendFriendRequest(@Param("userId") int userId, @Param("friendId") int friendId);

    // 更新好友关系状态（如接受、拒绝、阻止）
    @Update("UPDATE Friendship SET status = #{status} " +
            "WHERE user_id = #{userId} AND friend_id = #{friendId}")
    int updateFriendshipStatus(@Param("userId") int userId, @Param("friendId") int friendId, @Param("status") String status);

    // 删除好友（包括主动解除好友关系或取消好友请求）
    @Delete("DELETE FROM Friendship WHERE user_id = #{userId} AND friend_id = #{friendId}")
    int deleteFriendship(@Param("userId") int userId, @Param("friendId") int friendId);

    // 查询某个用户的所有好友（已接受的好友关系）
    @Select("SELECT * FROM Friendship WHERE user_id = #{userId} AND status = 'ACCEPTED'")
    List<Friendship> getAcceptedFriends(@Param("userId") int userId);

    // 查询某个用户的所有好友请求（待处理的好友请求）
    @Select("SELECT * FROM Friendship WHERE friend_id = #{userId} AND status = 'PENDING'")
    List<Friendship> getPendingFriendRequests(@Param("userId") int userId);

    // 查询好友关系的状态（判断两用户是否是好友、是否有请求或是否被阻止）
    @Select("SELECT * FROM Friendship WHERE user_id = #{userId} AND friend_id = #{friendId}")
    Friendship getFriendshipStatus(@Param("userId") int userId, @Param("friendId") int friendId);
}

package com.imdemo.mapper;

import com.imdemo.model.MessageType;
import com.imdemo.model.OfflineMessage;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface OfflineMessageMapper {

    // 插入离线消息
    @Insert("INSERT INTO Offline_Message (timestamp, sender_id, receiver_id, content, type) " +
            "VALUES (#{timestamp}, #{senderId}, #{receiverId}, #{content}, #{type.value})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOfflineMessage(OfflineMessage offlineMessage);

    // 根据接收者ID获取所有离线消息
    @Select("SELECT * FROM Offline_Message WHERE receiver_id = #{receiverId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "timestamp", column = "timestamp"),
            @Result(property = "senderId", column = "sender_id"),
            @Result(property = "receiverId", column = "receiver_id"),
            @Result(property = "content", column = "content"),
            @Result(property = "type", column = "type", javaType = MessageType.class, typeHandler = MessageTypeTypeHandler.class)
    })
    List<OfflineMessage> getOfflineMessagesByReceiverId(@Param("receiverId") Integer receiverId);

    // 根据消息ID删除离线消息
    @Delete("DELETE FROM Offline_Message WHERE id = #{id}")
    int deleteOfflineMessageById(@Param("id") Long id);

    // 删除用户所有的离线消息（用户上线后）
    @Delete("DELETE FROM Offline_Message WHERE receiver_id = #{receiverId}")
    int deleteOfflineMessagesByReceiverId(@Param("receiverId") Integer receiverId);
}

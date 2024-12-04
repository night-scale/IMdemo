package com.imdemo.mapper;

import com.imdemo.model.MessageType;
import com.imdemo.model.Source;
import org.apache.ibatis.annotations.*;

import java.util.List;
import com.imdemo.model.Message;
public interface MessageMapper {

    @Insert("INSERT INTO Message (timestamp, sender_id, receiver_id, content, source, type, group_id) " +
            "VALUES (#{timestamp}, #{senderId}, #{receiverId}, #{content}, #{source.value}, #{type.value}, #{groupId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertMessage(Message message);

    @Select("SELECT * FROM Message WHERE id = #{id}")
    @Results({
            @Result(property = "source", column = "source", javaType = Source.class, typeHandler = SourceTypeHandler.class),
            @Result(property = "type", column = "type", javaType = MessageType.class, typeHandler = MessageTypeTypeHandler.class)
    })
    Message getMessageById(Long id);

    @Select("SELECT * FROM Message WHERE receiver_id = #{receiverId} AND source = 0") // '0' for 'PRIVATE'
    @Results({
            @Result(property = "source", column = "source", javaType = Source.class, typeHandler = SourceTypeHandler.class),
            @Result(property = "type", column = "type", javaType = MessageType.class, typeHandler = MessageTypeTypeHandler.class)
    })
    List<Message> findPrivateMessagesByReceiverId(Integer receiverId);

    @Select("SELECT * FROM Message WHERE group_id = #{groupId} AND source = 1") // '1' for 'GROUP'
    @Results({
            @Result(property = "source", column = "source", javaType = Source.class, typeHandler = SourceTypeHandler.class),
            @Result(property = "type", column = "type", javaType = MessageType.class, typeHandler = MessageTypeTypeHandler.class)
    })
    List<Message> findGroupMessagesByGroupId(Long groupId);
}



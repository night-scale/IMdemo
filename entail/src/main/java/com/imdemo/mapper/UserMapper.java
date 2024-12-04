package com.imdemo.mapper;

import com.imdemo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Options;

public interface UserMapper {

    // 插入用户
    @Insert("INSERT INTO user_table (nickname, password, phone_number, email, user_portrait, created_at, updated_at) " +
            "VALUES (#{nickname}, #{password}, #{phoneNumber}, #{email}, #{userPortrait}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    // 根据ID获取用户
    @Select("SELECT id, nickname, password, phone_number, email, user_portrait, created_at, updated_at FROM user_table WHERE id = #{id}")
    User getUserById(int id);

    // 根据Email获取用户
    @Select("SELECT id, nickname, password, phone_number, email, user_portrait, created_at, updated_at FROM user_table WHERE email = #{email}")
    User getUserByEmail(String email);

    // 根据手机号获取用户
    @Select("SELECT id, nickname, password, phone_number, email, user_portrait, created_at, updated_at FROM user_table WHERE phone_number = #{phoneNumber}")
    User getUserByPhoneNumber(String phoneNumber);

    // 更新用户
    @Update("UPDATE user_table SET nickname = #{nickname}, password = #{password}, phone_number = #{phoneNumber}, " +
            "email = #{email}, user_portrait = #{userPortrait}, updated_at = #{updatedAt} WHERE id = #{id}")
    void updateUser(User user);
}

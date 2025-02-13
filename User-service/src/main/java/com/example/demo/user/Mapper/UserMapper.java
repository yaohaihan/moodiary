package com.example.demo.user.Mapper;

import com.example.demo.common.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper{
    @Insert("INSERT INTO tb_user(Username,Password,gender,email,status,avator) VALUES (#{username},#{password},#{gender},#{email},#{status},#{avator})")
    void addUser(String username, String password, String email, String gender, String status, byte[] avator);

    @Select("SELECT * FROM tb_user WHERE userId = #{userId}")
    User getUserById(int userId);


    @Select("SELECT * FROM tb_user WHERE email = #{email}")
    User getUserByEmail(String email);
}

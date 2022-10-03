package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface UserMapper {
    @Insert("insert into users (name,accountId,token,gmtCreate,gmtModified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
     void insert(User user);


}

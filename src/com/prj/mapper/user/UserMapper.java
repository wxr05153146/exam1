package com.prj.mapper.user;

import com.prj.entity.User;


public interface UserMapper {
    //登录
    public User login(User user);
    //添加
    public int addUser(User user);

}

package com.prj.server.user;

import com.prj.entity.User;

import java.util.List;

public interface UserServer {

    //登录
    public User login(User user);
    //添加
    public int addUser(User user);

}

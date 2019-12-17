package com.prj.server.user;

import com.prj.entity.User;
import com.prj.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("UserServerImpl")
public class UserServerImpl implements UserServer {
    @Autowired
    private UserMapper userMapper;

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    //登录
    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    //添加
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    //修改密码
    @Override
    public int updatePwd(int id, String newpwd) {
        return userMapper.updatePwd(id,newpwd);
    }

    //上传头像
    @Override
    public int userUpload(String url, int uid) {
        return userMapper.userUpload(url,uid);
    }
}

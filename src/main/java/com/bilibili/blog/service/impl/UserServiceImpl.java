package com.bilibili.blog.service.impl;

import com.bilibili.blog.dao.UserDao;
import com.bilibili.blog.pojo.User;
import com.bilibili.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username,password);
        return user;
    }
}

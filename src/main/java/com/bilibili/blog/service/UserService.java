package com.bilibili.blog.service;

import com.bilibili.blog.pojo.User;

public interface UserService {
    User checkUser(String username,String password);
}

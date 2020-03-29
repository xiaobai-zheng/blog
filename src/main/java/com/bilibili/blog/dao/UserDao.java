package com.bilibili.blog.dao;

import com.bilibili.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
}

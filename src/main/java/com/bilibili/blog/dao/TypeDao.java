package com.bilibili.blog.dao;

import com.bilibili.blog.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeDao extends JpaRepository<Type,Long> {

    Type findByName(String name);
}

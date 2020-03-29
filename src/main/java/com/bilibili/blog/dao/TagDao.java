package com.bilibili.blog.dao;

import com.bilibili.blog.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDao extends JpaRepository<Tag,Long> {

    Tag findByName(String name);
}

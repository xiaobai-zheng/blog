package com.bilibili.blog.service;

import com.bilibili.blog.pojo.Type;
import com.bilibili.blog.util.Msg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type getType(Long id);
    Page<Type> listPage(Pageable pageable);
    Msg saveType(Type type);
    Msg updateType(Type type);
    void deleteType(Long id);

    Type getTypeByName(String name);

    List<Type> listAll();
}

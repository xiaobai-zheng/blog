package com.bilibili.blog.service;

import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.util.Msg;
import com.bilibili.blog.vo.BlogVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listAllPage(Pageable pageable);
    Msg saveBlog(Blog blog);
    Msg updateBlog(Blog blog);
    void deleteBlog(Long id);
    Page<Blog> listSearch(Pageable pageable, BlogVo blogVo);
}

package com.bilibili.blog.service;

import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.pojo.Tag;
import com.bilibili.blog.pojo.Type;
import com.bilibili.blog.pojo.User;
import com.bilibili.blog.util.Msg;
import com.bilibili.blog.vo.BlogSubmit;
import com.bilibili.blog.vo.BlogVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    List<Blog> listByBlogTop(Integer size);
    Blog getAndConvert(Long id);
    Blog getBlog(Long id);
    Page<Blog> listAllPage(Pageable pageable);
    Msg saveBlog(BlogSubmit blogSubmit, User user, Type type, List<Tag> tags);
    void deleteBlog(Long id);
    Page<Blog> listSearch(Pageable pageable, BlogVo blogVo);
    Page<Blog> listSearch(Pageable pageable, String query);

    Page<Blog> listSearch(Pageable pageable, Long id);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();
}

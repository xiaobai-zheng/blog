package com.bilibili.blog.service;

import com.bilibili.blog.pojo.Comment;
import com.bilibili.blog.util.Msg;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Msg addComment(Comment comment);
}

package com.bilibili.blog.service.impl;

import com.bilibili.blog.dao.CommentDao;
import com.bilibili.blog.pojo.Comment;
import com.bilibili.blog.service.CommentService;
import com.bilibili.blog.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;
    List<Comment> replyList = new ArrayList<>();
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        List<Comment> comments = commentDao.findByBlogIdAndParentCommentNull(blogId, sort);
        List<Comment> commentViews = new ArrayList<>();
        commentViews.addAll(comments);
        for (Comment commentView : commentViews) {
            List<Comment> replyComments = commentView.getReplyComments();
            if (!ListUtils.isEmpty(replyComments)){
                for (Comment replyComment : replyComments) {
                    replyCommentList(replyComment);
                }
                replyComments.addAll(replyList);
                commentView.setReplyComments(replyComments);
                replyList = new ArrayList<>();
            }
        }
        return commentViews;
    }
//    遍历一个评论下的所有的子节点，包括孙子节点，
    private void replyCommentList(Comment comment){
//        replyList.add(comment);
        List<Comment> replyComments = comment.getReplyComments();
        if (!ListUtils.isEmpty(replyComments)){
            for (Comment replyComment : replyComments) {
                replyList.add(replyComment);
                replyCommentList(replyComment);
            }
        }
    }

    @Override
    public Msg addComment(Comment comment) {
        Long parentId = comment.getParentComment().getId();
        comment.setCreateTime(new Date());
        Msg msg = null;
        if (parentId >=0){
            comment.setParentComment(commentDao.getOne(parentId));

        }else {
            comment.setParentComment(null);
        }
        Comment save = commentDao.save(comment);
        Msg msg1 = initMsg(save);
        return null;
    }
    private Msg initMsg(Comment save) {
        Msg msg;
        if (save  == null){
            msg = Msg.fail();
            msg.setMessage("回复评论失败");
        }else{
            msg = Msg.success();
            msg.setMessage("回复评论成功");
        }
        return msg;
    }
}

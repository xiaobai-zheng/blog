package com.bilibili.blog.controller;

import com.bilibili.blog.pojo.Comment;
import com.bilibili.blog.pojo.User;
import com.bilibili.blog.service.BlogService;
import com.bilibili.blog.service.CommentService;
import com.bilibili.blog.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avatar}")
    private String avater;
    @GetMapping("/comments/{id}")
    public String comments(@PathVariable(name = "id") Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog::comment-area";
    }
    @PostMapping("/comment")
    public String post(Comment comment, RedirectAttributes attributes, HttpSession session){
        Long id = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(id));
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avater);
        }
        Msg msg = commentService.addComment(comment);
        attributes.addFlashAttribute("msg",msg);
        return "redirect:/comments/" + id;
    }
}

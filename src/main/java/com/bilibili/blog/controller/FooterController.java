package com.bilibili.blog.controller;

import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FooterController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/footer/blog")
    public String footerBlog(Model model){
        List<Blog> blogs = blogService.listByBlogTop(3);
        model.addAttribute("footerBlogs", blogs);
        return "footer::newBlog";
    }
}

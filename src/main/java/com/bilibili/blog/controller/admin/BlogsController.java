package com.bilibili.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogsController {
    @GetMapping("/blogs")
    public String blogs(){
        return "admin/blogs";
    }
}

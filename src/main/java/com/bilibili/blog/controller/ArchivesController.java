package com.bilibili.blog.controller;

import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchivesController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
       Map<String, List<Blog>> blogArchives =  blogService.archiveBlog();
       model.addAttribute("blogMaps",blogArchives);
       model.addAttribute("count",blogService.countBlog());
        return "archives";
    }
}

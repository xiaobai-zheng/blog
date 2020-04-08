package com.bilibili.blog.controller;

import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.pojo.Tag;
import com.bilibili.blog.service.BlogService;
import com.bilibili.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size =10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable(name = "id") Long id, Model model){
        List<Tag> tags = tagService.listAll();
        model.addAttribute("tags", tags);
        if (id<0){
            id = tags.get(0).getId();
        }
        Page<Blog> blogPage = blogService.listSearch(pageable, id);
        model.addAttribute("blogPage",blogPage);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}

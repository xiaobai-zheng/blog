package com.bilibili.blog.controller;

import com.bilibili.blog.service.BlogService;
import com.bilibili.blog.service.TagService;
import com.bilibili.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping
    public String index(@PageableDefault(size =10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("blogPage",blogService.listAllPage(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("blogs",blogService.listByBlogTop(10));
        return "index";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size =10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam(name = "query") String query, Model model){
        model.addAttribute("blogPage",blogService.listSearch(pageable,query));
        model.addAttribute("query",query);
        return "search";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable(name = "id") Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
}

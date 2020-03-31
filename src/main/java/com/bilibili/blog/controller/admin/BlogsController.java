package com.bilibili.blog.controller.admin;

import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.service.BlogService;
import com.bilibili.blog.service.TypeService;
import com.bilibili.blog.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogsController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @GetMapping("/blogs")
    //    按照id倒序排序
    public String blogs(@PageableDefault(size =10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("blogPage",blogService.listAllPage(pageable));
        model.addAttribute("types",typeService.listAll());
        return "admin/blogs";
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size =10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                         BlogVo blogVo, Model model){
        Page<Blog> blogPage = blogService.listSearch(pageable, blogVo);
        model.addAttribute("blogPage", blogPage);
        return "admin/blogs::table-container";
    }
}

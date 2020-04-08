package com.bilibili.blog.controller;

import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.pojo.Type;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/types/{id}")
    public String type(@PageableDefault(size =10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable(name = "id") Long id, Model model){
        List<Type> types = typeService.listAll();
        model.addAttribute("types", types);
        if (id<0){
            id = types.get(0).getId();
        }
        BlogVo blogVo = new BlogVo();
        blogVo.setTypeId(id);
        Page<Blog> blogPage = blogService.listSearch(pageable, blogVo);
        model.addAttribute("blogPage",blogPage);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}

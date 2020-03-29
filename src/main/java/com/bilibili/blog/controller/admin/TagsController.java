package com.bilibili.blog.controller.admin;

import com.bilibili.blog.pojo.Tag;
import com.bilibili.blog.service.TagService;
import com.bilibili.blog.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagsController {
    @Autowired
    TagService tagService;
    @GetMapping("/tags")
//    按照id倒序排序
    public String tags(@PageableDefault(size =10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Tag> tagPage = tagService.listPage(pageable);
        model.addAttribute("tagPage",tagPage);
        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(){
        return "admin/tags-input";
    }
    @PostMapping("/tags/input")
    public String addOrAlter(@Valid Tag tag, BindingResult bindingResult,@RequestParam("pageNum") Integer page, RedirectAttributes attributes,Model model){
        if (bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError("name");
            String error = fieldError.getDefaultMessage();
            model.addAttribute("error",error);
            return "admin/tags-input";
        }
        if (tagService.getTagByName(tag.getName()) != null){
            model.addAttribute("error","不能添加重复的标签");
            return "admin/tags-input";
        }
        if (tag.getId() !=null){
            Msg msg = tagService.updateTag(tag);
            attributes.addFlashAttribute("msg",msg);
            return "redirect:/admin/tags?page="+page;

        }else{
            Msg msg = tagService.saveTag(tag);
            attributes.addFlashAttribute("msg",msg);
            return "redirect:/admin/tags";
        }
    }
    @GetMapping("/tags/{id}/input")
    public String alterInput(@PathVariable(name = "id") Long id,Model model,@RequestParam(name = "pageNum") Integer pageNum){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag",tag);
        model.addAttribute("pageNum",pageNum);
        return "admin/tags-input";
    }
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg",Msg.success());
        return "redirect:/admin/tags";
    }
}

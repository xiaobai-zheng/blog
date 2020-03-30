package com.bilibili.blog.controller.admin;

import com.bilibili.blog.pojo.Type;
import com.bilibili.blog.service.TypeService;
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
public class TypesController {
    @Autowired
    TypeService typeService;
    @GetMapping("/types")
//    按照id倒序排序
    public String types(@PageableDefault(size =10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Type> typePage = typeService.listPage(pageable);
        model.addAttribute("typePage",typePage);
        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(){
        return "admin/types-input";
    }
    @PostMapping("/types/input")
    public String add(@Valid Type type, BindingResult bindingResult,@RequestParam("pageNum") Integer page, RedirectAttributes attributes,Model model){
        if (bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError("name");
            String error = fieldError.getDefaultMessage();
            model.addAttribute("error",error);
            return "admin/types-input";
        }
        if (typeService.getTypeByName(type.getName()) != null){
            model.addAttribute("error","不能添加重复的分类");
            return "admin/types-input";
        }
        if (type.getId() !=null){
            Msg msg = typeService.updateType(type);
            attributes.addFlashAttribute("msg",msg);
            return "redirect:/admin/types?page="+page;

        }else{
            Msg msg = typeService.saveType(type);
            attributes.addFlashAttribute("msg",msg);
            return "redirect:/admin/types";
        }
    }
    @GetMapping("/types/{id}/input")
    public String alterInput(@PathVariable(name = "id") Long id,Model model,@RequestParam(name = "pageNum") Integer pageNum){
        Type type = typeService.getType(id);
        model.addAttribute("type",type);
        model.addAttribute("pageNum",pageNum);
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id,RedirectAttributes attributes){
        Type type = typeService.getType(id);
        typeService.deleteType(id);
        Msg msg = Msg.success();
        msg.setMessage("成功删除了分类："+type.getName());
        attributes.addFlashAttribute("msg", msg);
        return "redirect:/admin/types";
    }
}

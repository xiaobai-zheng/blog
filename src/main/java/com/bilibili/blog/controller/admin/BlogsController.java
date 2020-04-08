package com.bilibili.blog.controller.admin;

import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.pojo.Tag;
import com.bilibili.blog.pojo.Type;
import com.bilibili.blog.pojo.User;
import com.bilibili.blog.service.BlogService;
import com.bilibili.blog.service.TagService;
import com.bilibili.blog.service.TypeService;
import com.bilibili.blog.util.Msg;
import com.bilibili.blog.vo.BlogSubmit;
import com.bilibili.blog.vo.BlogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class BlogsController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    //    按照id倒序排序，返回到博客列表页面
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size =3,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("blogPage",blogService.listAllPage(pageable));
        model.addAttribute("types",typeService.listAll());
        return "admin/blogs";
    }
//    博客搜索
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size =3,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                         BlogVo blogVo, Model model){
        Page<Blog> blogPage = blogService.listSearch(pageable, blogVo);
        model.addAttribute("blogPage", blogPage);
        return "admin/blogs::table-container";
    }
//    返回到博客发布页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        init_type_tag(model);
        model.addAttribute("blogSubmit",new BlogSubmit());
        return "admin/blogs-input";
    }
//    发布与保存
    @PostMapping("/blogs")
    @Transactional
    public String post(BlogSubmit blogSubmit, RedirectAttributes attributes, HttpSession session){
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogSubmit, blog);
        Type type = typeService.getType(blogSubmit.getTypeId());
        List<Tag> tags = tagService.listByIds(blogSubmit.getTagIds());
        User user = (User) session.getAttribute("user");
        Msg msg = blogService.saveBlog(blogSubmit,user,type,tags);
        attributes.addFlashAttribute("msg",msg);
        return "redirect:/admin/blogs";
    }
//    返回到博客编辑页面
    @GetMapping("/blogs/{id}/alter")
    public String alter_input(@PathVariable(name = "id") Long id, Model model){
        this.init_type_tag(model);
        Blog blog = blogService.getBlog(id);
        BlogSubmit blogSubmit = new BlogSubmit();
        BeanUtils.copyProperties(blog,blogSubmit);
        List<Tag> tags = blog.getTags();
        List<Long> tagIds = tags.stream().map(tag -> tag.getId()).collect(Collectors.toList());
        blogSubmit.setTagIds(StringUtils.join(tagIds,","));
        blogSubmit.setTypeId(blog.getType().getId());
        model.addAttribute("blogSubmit",blogSubmit);
        return "admin/blogs-input";
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable(name="id") Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        Msg msg = Msg.success();
        msg.setMessage("成功删除了博客");
        attributes.addFlashAttribute("msg",msg);
        return "redirect:/admin/blogs";
    }
    private void init_type_tag(Model model) {
        model.addAttribute("types", typeService.listAll());
        model.addAttribute("tags", tagService.listAll());
    }

}

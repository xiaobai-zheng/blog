package com.bilibili.blog.controller.admin;

import com.bilibili.blog.pojo.User;
import com.bilibili.blog.service.UserService;
import com.bilibili.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserController {
    @GetMapping
    public String loginPage(HttpSession session){
        if(session.getAttribute("user") != null){
            return "admin/index";
        }
        return "admin/login";
    }
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public String login(@RequestParam(name = "username")String username, @RequestParam(name = "password") String password,
                        HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){

        User user = userService.checkUser(username, MD5Utils.code(password));
        if (user == null){
            attributes.addFlashAttribute("error","用户名和密码错误");//重定向传参
            return "redirect:/admin";
        }else {
            HttpSession session = request.getSession();
            user.setPassword(null);
            session.setAttribute("user",user);
            String id = session.getId();
            Cookie cookie = new Cookie("JSESSIONID",id);
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
            return "admin/index";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "admin/login";
    }

}

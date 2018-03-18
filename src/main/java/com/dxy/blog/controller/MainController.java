package com.dxy.blog.controller;

import com.dxy.blog.entity.Authority;
import com.dxy.blog.entity.User;
import com.dxy.blog.service.AuthorityService;
import com.dxy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private static final Long ROLE_USER_AUTHORITY_ID=2L;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 用户登录
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登录失败，用户名或密码错误！");
        return "login";
    }

    /**
     * 注册用户
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("register")
    public String registerUser(User user) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorities);
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/search")
    public String search(){
        return "search";
    }
}

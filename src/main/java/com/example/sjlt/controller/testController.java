package com.example.sjlt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sjlt.entity.Label;
import com.example.sjlt.entity.User;
import com.example.sjlt.service.ILabelService;
import com.example.sjlt.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/test")
public class testController {
    @Resource
    private IUserService userService;
    @Resource
    private ILabelService labelService;
    @GetMapping
    public String test(){
        System.out.println("进来了");
        return "backend/login";
    }
    @GetMapping("/index")
    public String index(){
        System.out.println("进来了");
        return "front/login";
    }
    @GetMapping("/register")
    public String register(){
        return "front/register";
    }
    @GetMapping("/head")
    public String head(HttpSession session, HttpServletRequest request){
        User user = (User) session.getAttribute("user");
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getUserId());
        User byId = userService.getOne(queryWrapper);
        request.setAttribute("byId",byId);
        return "front/head";
    }
    @GetMapping("/publish")
    public String publish(HttpServletRequest request){
        List<Label> list = labelService.list();
        request.setAttribute("list",list);
        for (Label label:list){
            System.out.println(label);
        }
        return "front/publish";
    }
    @GetMapping("/up")
    public String upload(){
        return "upload";
    }
}

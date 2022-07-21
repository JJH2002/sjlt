package com.example.sjlt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sjlt.domain.genRandomNum;
import com.example.sjlt.entity.Article;
import com.example.sjlt.entity.Label;
import com.example.sjlt.service.IArticleService;
import com.example.sjlt.domain.ReturnObject;
import com.example.sjlt.entity.User;
import com.example.sjlt.service.ILabelService;
import com.example.sjlt.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  用户表前端控制器
 * </p>
 *
 * @author itcast
 * @since 2022-07-03
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IArticleService articleService;
    @Resource
    private ILabelService labelService;
    //用户登录
    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestBody User user,HttpServletRequest request){
        //System.out.println(user_name+"==="+user_password);
        System.out.println(user);
        ReturnObject returnObject=new ReturnObject();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();

        //QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",user.getUserName()).eq("user_password",user.getUserPassword());
        User one = userService.getOne(queryWrapper);
            if (one==null){
                System.out.println("登录失败");
                returnObject.setCode("0");
                returnObject.setMessage("登录失败，账号或密码错误");
            }else {
                if (one.getUserState()==0){
                    returnObject.setCode("0");
                    returnObject.setMessage("用户状态异常");
                }else{
                    returnObject.setCode("1");
                    returnObject.setRetData(one);
                    request.getSession().setAttribute("user",one);
                }
            }
        System.out.println(returnObject);
        return returnObject;
    }
    //用户注册
    @PostMapping("/logon")
    @ResponseBody
    public Object logon(@RequestBody User user,HttpServletRequest request){
        System.out.println(user);
        ReturnObject returnObject=new ReturnObject();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_nickname",user.getUserNickname());
        User one1 = userService.getOne(queryWrapper);
        try {
            user.setUserSex(URLDecoder.decode(user.getUserSex(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LocalDate now = LocalDate.now();
        user.setUserAge(user.getUserBirthday().until(now).getYears());
        if (one1==null){
            try {
                user.setUserName(genRandomNum.genRandomNum());
                user.setUserType(0);
                user.setUserState(1);
                boolean save = userService.save(user);
                if (save){
                    User one = userService.getOne(queryWrapper);
                    returnObject.setCode("1");
                    returnObject.setMessage("注册成功");
                    returnObject.setRetData(one);
                    request.getSession().setAttribute("user",one);
                }else {
                    returnObject.setCode("0");
                    returnObject.setMessage("注册失败");
                }
            } catch (Exception e) {
                returnObject.setCode("0");
                returnObject.setMessage("请检查你输入的信息是否完整");
                e.printStackTrace();
            }
        }else {
            returnObject.setCode("0");
            returnObject.setMessage("用户名已存在");
        }
        return returnObject;
    }
    //管理员登录跳转
    @GetMapping("/list")
    public String list(/*@RequestBody User user,HttpSession session,*/HttpServletRequest request){
        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.orderByDesc("article_like_count");
        //Object user1 = session.getAttribute("user");
        //QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        List<Label> labelList = labelService.list();
        /*if (user.getUserType()==0){
            articleQueryWrapper.eq("state",1);
            queryWrapper.eq("user_id",user1);
            User byId = userService.getOne(queryWrapper);
            List<Article> list = articleService.list(articleQueryWrapper);
            request.setAttribute("list",list);
            request.setAttribute("byId",byId);
            request.setAttribute("labelList",labelList);
            return "";
        }else {*/
            articleQueryWrapper.eq("status",0);
            List<Article> list = articleService.list(articleQueryWrapper);
            List<User> userList = userService.list();
            request.setAttribute("userList",userList);
            request.setAttribute("labelList",labelList);
            request.setAttribute("list",list);
            return "backend/administrators";
        //}
    }
    //用户的登录跳转
    @GetMapping("/logon")
    public String list(/*@RequestBody User user,*/HttpSession session,HttpServletRequest request){
        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.orderByDesc("article_like_count");
        User user1 = (User) session.getAttribute("user");
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        List<Label> labelList = labelService.list();
        articleQueryWrapper.eq("status",1);
        queryWrapper.eq("user_id",user1.getUserId());
        User byId = userService.getOne(queryWrapper);
        List<Article> list = articleService.list(articleQueryWrapper);
        request.setAttribute("list",list);
        request.setAttribute("byId",byId);
        request.setAttribute("labelList",labelList);
        return "front/index";
    }

    //禁用与启用用户
    @GetMapping("/modifyState")
    @ResponseBody
    public String modifyState(Long userId,Integer userState){
        User user=new User();
        user.setUserId(userId);
        user.setUserState(userState);
        userService.updateById(user);
        return "操作成功";
    }
    //用户详细信息（基本信息和发布的帖子信息）
    @GetMapping("/detailedInformation")
    public String information(Long userId,HttpServletRequest request){
        System.out.println(userId);
        User byId = userService.getById(userId);
        QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Article> list = articleService.list(queryWrapper);
        request.setAttribute("byId",byId);
        request.setAttribute("list",list);
        return "front/userPersonalInformation";
    }
}

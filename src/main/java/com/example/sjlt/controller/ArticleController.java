package com.example.sjlt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.sjlt.domain.ReturnObject;
import com.example.sjlt.domain.UserComment;
import com.example.sjlt.entity.Article;
import com.example.sjlt.entity.Comment;
import com.example.sjlt.entity.Label;
import com.example.sjlt.entity.User;
import com.example.sjlt.service.IArticleService;
import com.example.sjlt.service.ICommentService;
import com.example.sjlt.service.ILabelService;
import com.example.sjlt.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  帖子表前端控制器
 * </p>
 *
 * @author itcast
 * @since 2022-07-03
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private IArticleService articleService;
    @Resource
    private ICommentService commentService;
    @Resource
    private IUserService userService;
    @Resource
    private ILabelService labelService;
    //查询对应标签的帖子
    @GetMapping("/lookup")
    public Object lookup(Long labelId,HttpServletRequest request){
        QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("label_id",labelId).eq("status",1).orderByDesc("article_like_count");
        List<Article> list = articleService.list(queryWrapper);
        Label byId = labelService.getById(labelId);
        String labelName = byId.getLabelName();
        request.setAttribute("labelName",labelName);
        request.setAttribute("list",list);
        return "front/index";
    }
    //发布帖子
    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody Article article, HttpSession session){
        User user = (User) session.getAttribute("user");
        try {
            article.setArticleTitle(URLDecoder.decode(article.getArticleTitle(),"utf-8"));
            article.setArticleContent(URLDecoder.decode(article.getArticleContent(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ReturnObject returnObject=new ReturnObject();
        article.setUserId(user.getUserId());
        article.setUserId(1001l);
        article.setArticleLikeCount(0l);
        article.setArticleViews(0l);
        article.setArticleCommentCount(0l);
        article.setStatus(0);
        try {
            boolean save = articleService.save(article);
            if (save){
                returnObject.setMessage("发布成功,请等待管理员审核");
                returnObject.setCode("1");
            }else {
                returnObject.setMessage("系统忙，请稍后重试");
                returnObject.setCode("0");
            }
        } catch (Exception e) {
            returnObject.setMessage("系统忙，请稍后重试");
            returnObject.setCode("0");
            e.printStackTrace();
        }
        return returnObject;
    }
    //查看帖子详细信息
    @GetMapping("/detailed")
    public String detailed(Long articleId, HttpServletRequest request){
        Article byId = articleService.getById(articleId);
        byId.setArticleViews(byId.getArticleViews()+1);
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("article_id",articleId);
        User userById = userService.getById(byId.getUserId());
        List<Comment> list = commentService.list(queryWrapper);
        List<UserComment> userCommentList=new ArrayList<>();
        for (Comment comment:list){
            UserComment userComment=new UserComment();
            userComment.setComment(comment);
            userComment.setUser(userService.getById(comment.getUserId()));
            userCommentList.add(userComment);
        }
        /*List<Comment> commentList=new ArrayList<>();
        List<Comment> comments=new ArrayList<>();
        for (Comment comment:list){
            //判断他是否为评论的评论
            if (comment.getParentCommentId()==null){
                commentList.add(comment);
            }else {
                comments.add(comment);
            }
        }*/
        for (UserComment userComment:userCommentList){
            System.out.println(userComment);
        }
        request.setAttribute("userById",userById);
        /*request.setAttribute("commentList",commentList);
        request.setAttribute("comments",comments);*/
        request.setAttribute("userCommentList",userCommentList);
        request.setAttribute("byId",byId);
        return "front/post";
    }
    //查看用户发布的帖子
    @GetMapping("/see")
    @ResponseBody
    public List see(Long userId){
        QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Article> list = articleService.list(queryWrapper);
        return list;
    }
    //审核
    //@PostMapping("/toExamine")
    @GetMapping("/toExamine")
    @ResponseBody
    public Object toExamine(/*@RequestBody Article article*/Long articleId,Integer status){
        ReturnObject returnObject=new ReturnObject();
        //QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        //queryWrapper.eq("article_id",article.getArticleId()).eq("state",article.getStatus());
        //queryWrapper.eq("article_id",articleId).eq("state",status);
        Article article=new Article();
        article.setArticleId(articleId);
        article.setStatus(status);
        try {
            boolean update = articleService.updateById(article);
            if (update){
                returnObject.setCode("1");
                returnObject.setMessage("审核成功");
            }else {
                returnObject.setCode("0");
                returnObject.setMessage("系统忙");
            }
        } catch (Exception e) {
            returnObject.setCode("0");
            returnObject.setMessage("系统忙");
            e.printStackTrace();
        }
        return returnObject;
    }
    @GetMapping("/delete")
    private String delete(Long articleId,Long userId){
        boolean b = articleService.removeById(articleId);
        UserController userController=new UserController();
        //userController.information(userId);
        return "front/userPersonalInformation";
    }
}

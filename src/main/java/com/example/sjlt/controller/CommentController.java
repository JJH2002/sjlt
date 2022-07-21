package com.example.sjlt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sjlt.domain.ReturnObject;
import com.example.sjlt.entity.Article;
import com.example.sjlt.entity.Comment;
import com.example.sjlt.entity.User;
import com.example.sjlt.service.IArticleService;
import com.example.sjlt.service.ICommentService;
import com.example.sjlt.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>
 *  评论表前端控制器
 * </p>
 *
 * @author itcast
 * @since 2022-07-05
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Resource
    public ICommentService commentService;
    @Resource
    public IUserService userService;
    @Resource
    public IArticleService articleService;
    //查看用户评论
    @GetMapping("/see")
    @ResponseBody
    public List see(Long userId){
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Comment> list = commentService.list(queryWrapper);
        return list;
    }
    //发布评论
    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody Comment comment, HttpSession session) throws UnsupportedEncodingException {
        ReturnObject returnObject=new ReturnObject();
        User user = (User) session.getAttribute("user");
        comment.setCommentContent(URLDecoder.decode(comment.getCommentContent(),"utf-8"));
        comment.setUserId(user.getUserId());
        System.out.println(comment);
        try {
            comment.setCommentLikeCount(0l);
            boolean save = commentService.save(comment);
            if (save){
                Article byId = articleService.getById(comment.getArticleId());
                byId.setArticleCommentCount(byId.getArticleCommentCount()+1);
                articleService.updateById(byId);
                returnObject.setCode("1");
                returnObject.setMessage("发布成功");
            }else {
                returnObject.setCode("0");
                returnObject.setMessage("系统忙，请稍后重试");
            }
        } catch (Exception e) {
            returnObject.setCode("0");
            returnObject.setMessage("系统忙，请稍后重试");
            e.printStackTrace();
        }
        return returnObject;
    }
    //删除评论
    @DeleteMapping
    public Object delete(Long commentId,HttpSession session){
        ReturnObject returnObject=new ReturnObject();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_type",1);
        List<User> list = userService.list(queryWrapper);
        Comment byId = commentService.getById(commentId);
        User user = (User) session.getAttribute("user");
        try {
            for (User user1 : list) {
                if (user.getUserId() == byId.getUserId() || user.getUserId()  == byId.getArticleId() || user.getUserId()  == user1.getUserId()) {
                    boolean b = commentService.removeById(commentId);
                    if (b) {
                        returnObject.setCode("1");
                        returnObject.setMessage("删除成功");
                    } else {
                        returnObject.setCode("0");
                        returnObject.setMessage("系统忙");
                    }

                } else {
                    returnObject.setCode("0");
                    returnObject.setMessage("不好意思，你没权力删除该评论，不过你可以考虑举报他");
                }
            }
        }catch (Exception e) {
            returnObject.setCode("0");
            returnObject.setMessage("系统忙");
            e.printStackTrace();
        }
        return returnObject;
    }

}

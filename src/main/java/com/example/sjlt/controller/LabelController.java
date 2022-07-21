package com.example.sjlt.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.example.sjlt.domain.ReturnObject;
import com.example.sjlt.entity.Label;
import com.example.sjlt.service.ILabelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  标签表前端控制器
 * </p>
 *
 * @author itcast
 * @since 2022-07-04
 */
@Controller
@RequestMapping("/label")
public class LabelController {
    @Resource
    private ILabelService labelService;
    @GetMapping
    public List lookup(){
        return null;
    }
    //添加标签
    @PostMapping("/add")
    @ResponseBody
    public Object add(/*String labelName*/@RequestBody Label label){
        //Label label=new Label();
        //label.setLabelName(labelName);
        System.out.println("yyy");
        ReturnObject returnObject=new ReturnObject();
        try {
            boolean save = labelService.save(label);
            if (save){
                returnObject.setCode("1");
                returnObject.setMessage("添加成功");
            }else {
                returnObject.setCode("0");
                returnObject.setMessage("添加失败");
            }
        } catch (Exception e) {
            returnObject.setCode("0");
            returnObject.setMessage("系统忙");
            e.printStackTrace();
        }
        return returnObject;
    }
    //删除标签
    @DeleteMapping("/delete")
    @ResponseBody
    public Object delete(Long labelId){
        ReturnObject returnObject=new ReturnObject();
        try {
            boolean b = labelService.removeById(labelId);
            if (b){
                returnObject.setCode("1");
                returnObject.setMessage("删除成功");
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
    //查看全部标签
    @GetMapping("/see")
    public String see(HttpServletRequest request){
        List<Label> list = labelService.list();
        request.setAttribute("list",list);
        return "front/allPartitions";
    }
}

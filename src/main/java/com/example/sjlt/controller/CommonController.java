package com.example.sjlt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;
    //文件上传
    @PostMapping("/upload")
    public Object upload(MultipartFile file){
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        System.out.println(file.toString());
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //字符串截取
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        //String fileName = UUID.randomUUID().toString()+substring;
        System.out.println(file.getOriginalFilename());
        String fileName =file.getOriginalFilename();
        //System.out.println(basePath+originalFilename);
        File dir=new File(basePath);
        //判断目录是否存在
        if (!dir.exists()){
            //不存在，则创建
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath+originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
    //文件下载
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        System.out.println(name);
        FileInputStream fileInputStream=null;
        ServletOutputStream outputStream=null;
        try {
            //输入流，通过输入流读取文件内容
            fileInputStream=new FileInputStream(new File(basePath+name));
            //输出流，通过输出流将文件回写到浏览器展示图片
            outputStream = response.getOutputStream();
            //表示读取的文件为图片
            response.setContentType("image/jpeg");
            int len=0;
            byte[] bytes=new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileInputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

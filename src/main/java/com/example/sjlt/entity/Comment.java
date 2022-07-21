package com.example.sjlt.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 评论表
    * </p>
*
* @author itcast
* @since 2022-07-05
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 评论id
            */
    @TableId        //使用雪花算法自动填充
    @JsonSerialize(using = ToStringSerializer.class)    //输出转换为字符串类型，可以保证数据的不失真。
    private Long commentId;

            /**
            * 用户id
            */
    private Long userId;

            /**
            * 博文id
            */
    private Long articleId;

            /**
            * 评论时间
            */
    @TableField(fill = FieldFill.INSERT) //插入时填充字段
    private LocalDateTime commentDate;

            /**
            * 点赞数
            */
    private Long commentLikeCount;

            /**
            * 评论内容
            */
    private String commentContent;

            /**
            * 父评论
            */
    private Long parentCommentId;

            /**
            * 标签
            */
    private String parentContent;


}

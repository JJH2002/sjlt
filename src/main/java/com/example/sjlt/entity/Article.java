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
    * 帖子表
    * </p>
*
* @author itcast
* @since 2022-07-03
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 帖子id
            */
    @TableId        //使用雪花算法自动填充
    @JsonSerialize(using = ToStringSerializer.class)    //输出转换为字符串类型，可以保证数据的不失真。
    private Long articleId;

            /**
            * 用户id
            */
    private Long userId;

            /**
            * 帖子标题
            */
    private String articleTitle;

            /**
            * 帖子内容
            */
    private String articleContent;

            /**
            * 点赞数
            */
    private Long articleLikeCount;

            /**
            * 发布日期
            */
    @TableField(fill = FieldFill.INSERT) //插入时填充字段
    private LocalDateTime articleDate;

            /**
            * 浏览量
            */
    private Long articleViews;

            /**
            * 总评论
            */
    private Long articleCommentCount;

            /**
            * 标签
            */
    private Long labelId;

            /**
             * 审核状态
             */
    private Integer status;

}

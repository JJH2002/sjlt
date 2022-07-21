package com.example.sjlt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 标签表
    * </p>
*
* @author itcast
* @since 2022-07-04
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Label implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 标签id
            */
    @TableId        //使用雪花算法自动填充
    @JsonSerialize(using = ToStringSerializer.class)    //输出转换为字符串类型，可以保证数据的不失真。
    private Long labelId;

            /**
            * 标签名
            */
    private String labelName;

            /**
            * 标签描述
            */
    private String labelDecription;

            /**
            * 标签别名
            */
    private String labelOtherName;


}

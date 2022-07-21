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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* <p>
    * 用户表
    * </p>
*
* @author itcast
* @since 2022-07-03
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class User implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 用户ID
            */
    @TableId        //使用雪花算法自动填充
    @JsonSerialize(using = ToStringSerializer.class)    //输出转换为字符串类型，可以保证数据的不失真。
    private Long userId;

            /**
            * 用户名
            */
    private String userName;

            /**
            * 用户密码
            */
    private String userPassword;

            /**
            * 用户邮件
            */
    private String userEmail;

            /**
            * 用户头像
            */
    private String userPhoto;

            /**
            * 用户创建时间
            */
    @TableField(fill = FieldFill.INSERT) //插入时填充字段
    private LocalDateTime userCreatetime;

            /**
            * 用户出生日期
            */
    private LocalDate userBirthday;

            /**
            * 用户年龄
            */
    private Integer userAge;

            /**
            * 用户电话
            */
    private Integer userNumber;

            /**
            * 用户昵称
            */
    private String userNickname;

            /**
            * 用户性别
            */
    private String userSex;

            /**
            * 用户类型
            */
    private Integer userType;

            /**
             * 用户状态(0为禁用，1为启用)
             */
    private Integer userState;

}

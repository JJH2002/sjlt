package com.example.sjlt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sjlt.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  用户表Mapper 接口
 * </p>
 *
 * @author itcast
 * @since 2022-07-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

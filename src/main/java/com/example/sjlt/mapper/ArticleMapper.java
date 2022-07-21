package com.example.sjlt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sjlt.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itcast
 * @since 2022-07-03
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}

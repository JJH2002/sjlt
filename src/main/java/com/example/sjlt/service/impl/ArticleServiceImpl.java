package com.example.sjlt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sjlt.entity.Article;
import com.example.sjlt.mapper.ArticleMapper;
import com.example.sjlt.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itcast
 * @since 2022-07-03
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}

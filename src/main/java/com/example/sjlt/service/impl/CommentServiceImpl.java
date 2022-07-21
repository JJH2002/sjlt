package com.example.sjlt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sjlt.entity.Comment;
import com.example.sjlt.mapper.CommentMapper;
import com.example.sjlt.service.ICommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itcast
 * @since 2022-07-05
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}

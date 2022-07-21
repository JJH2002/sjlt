package com.example.sjlt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sjlt.entity.User;
import com.example.sjlt.mapper.UserMapper;
import com.example.sjlt.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}


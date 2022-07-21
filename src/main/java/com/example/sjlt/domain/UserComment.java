package com.example.sjlt.domain;

import com.example.sjlt.entity.Comment;
import com.example.sjlt.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserComment{
    private User user;
    private Comment comment;
}

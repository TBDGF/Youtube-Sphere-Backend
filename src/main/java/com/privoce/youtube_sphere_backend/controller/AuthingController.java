package com.privoce.youtube_sphere_backend.controller;

import cn.authing.core.graphql.GraphQLException;
import cn.authing.core.types.User;
import com.privoce.youtube_sphere_backend.service.AuthingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/authing")
public class AuthingController {
    @Autowired
    AuthingService authingService;

    @GetMapping("/search")
    public List<?> searchByKeyword( String keyword) throws IOException, GraphQLException {
        System.out.println("get search request: "+keyword);
        return authingService.searchByKeyword(keyword);
    }

    @GetMapping("/user")
    public User getAuthingUserById(String userId) throws IOException, GraphQLException {
        return authingService.getUserById(userId);
    }
}

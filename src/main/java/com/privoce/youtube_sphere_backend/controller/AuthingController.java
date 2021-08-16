package com.privoce.youtube_sphere_backend.controller;

import cn.authing.core.graphql.GraphQLException;
import cn.authing.core.types.User;
import com.privoce.youtube_sphere_backend.entity.SphereUser;
import com.privoce.youtube_sphere_backend.service.AuthingService;
import com.privoce.youtube_sphere_backend.service.GraphDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/authing")
public class AuthingController {
    @Autowired
    AuthingService authingService;
    @Autowired
    GraphDBService graphDBService;

    @GetMapping("/search")
    public List<?> searchByKeyword( String keyword) throws IOException, GraphQLException {
        System.out.println("get search request: "+keyword);
        return authingService.searchByKeyword(keyword);
    }

    @GetMapping("/user")
    public User getAuthingUserById(String userId) throws IOException, GraphQLException {
        return authingService.getUserById(userId);
    }

    @GetMapping("/connect")
    public List<User> getFriends(String userId) throws IOException {
        List<User> result = new ArrayList<>();
        List<SphereUser> rawUsers = graphDBService.getFriends(userId);
        for(SphereUser user:rawUsers) {
            result.add(authingService.getUserById(user.getUserId()));
        }
        return result;
    }
}

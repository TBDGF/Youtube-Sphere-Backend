package com.privoce.youtube_sphere_backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.privoce.youtube_sphere_backend.entity.SphereUser;
import com.privoce.youtube_sphere_backend.entity.VideoInfo;
import com.privoce.youtube_sphere_backend.service.GraphDBService;
import com.privoce.youtube_sphere_backend.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SphereUserController {
    @Autowired
    GraphDBService graphDBService;
    @Autowired
    YoutubeService youtubeService;


    @GetMapping("/connect")
    public List<SphereUser> getFriends(String userId){
        return graphDBService.getFriends(userId);
    }

    @GetMapping("/connect/liked")
    public JSONObject getFriendsLiked(String userId){
        return youtubeService.getVideoInfo("https://www.youtube.com/watch?v=Ausb8E2zJJk");
    }
}

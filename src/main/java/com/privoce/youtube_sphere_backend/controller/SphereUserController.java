package com.privoce.youtube_sphere_backend.controller;

import com.privoce.youtube_sphere_backend.entity.SphereUser;
import com.privoce.youtube_sphere_backend.entity.VideoInfo;
import com.privoce.youtube_sphere_backend.service.GraphDBService;
import com.privoce.youtube_sphere_backend.service.YoutubeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class SphereUserController {
    @Autowired
    GraphDBService graphDBService;
    @Autowired
    YoutubeService youtubeService;


    @GetMapping("/connect")
    public List<SphereUser> getFriends(String userId) {
        return graphDBService.getFriends(userId);
    }

    @GetMapping("/connect/liked")
    public List<VideoInfo> getFriendsLiked(String userId,Integer num){
        List<VideoInfo> videoInfos=new ArrayList<>();
        return videoInfos.size()>num?videoInfos.subList(0,num):videoInfos;
    }
}

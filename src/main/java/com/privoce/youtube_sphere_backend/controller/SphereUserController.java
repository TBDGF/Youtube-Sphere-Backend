package com.privoce.youtube_sphere_backend.controller;

import com.privoce.youtube_sphere_backend.entity.SphereUser;
import com.privoce.youtube_sphere_backend.entity.VideoInfo;
import com.privoce.youtube_sphere_backend.service.GraphDBService;
import com.privoce.youtube_sphere_backend.service.RecordService;
import com.privoce.youtube_sphere_backend.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SphereUserController {
    @Autowired
    GraphDBService graphDBService;
    @Autowired
    YoutubeService youtubeService;
    @Autowired
    RecordService recordService;


    @GetMapping("/connect")
    public List<SphereUser> getFriends(String userId){
        return graphDBService.getFriends(userId);
    }

    @GetMapping("/connect/liked")
    public List<VideoInfo> getFriendsLiked(String userId){
        List<SphereUser> list=graphDBService.getFriends(userId);
        List<VideoInfo> videoInfos=new ArrayList<>();
        for (SphereUser friend:list){
            List<String> likedList= recordService.getLiked(friend.getUserId());
            for (String url:likedList){
                VideoInfo temp= youtubeService.getVideoInfo(url);
                temp.setUserId(friend.getUserId());
                temp.setNickname(friend.getNickname());
                videoInfos.add(temp);
            }
        }
        return videoInfos;
    }
}

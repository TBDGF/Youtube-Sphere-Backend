package com.privoce.youtube_sphere_backend.controller;

import com.privoce.youtube_sphere_backend.entity.Record;
import com.privoce.youtube_sphere_backend.entity.SphereUser;
import com.privoce.youtube_sphere_backend.entity.VideoInfo;
import com.privoce.youtube_sphere_backend.service.GraphDBService;
import com.privoce.youtube_sphere_backend.service.RecordService;
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
    @Autowired
    RecordService recordService;


    @GetMapping("/connect")
    public List<SphereUser> getFriends(String userId) {
        return graphDBService.getFriends(userId);
    }

    @GetMapping("/connect/liked")
    public List<VideoInfo> getFriendsLiked(String userId,Integer num){
        if (num==null)
            num=4;
        Map<String,Integer> map=new HashMap<>();
        List<SphereUser> list=graphDBService.getFriends(userId);
        List<VideoInfo> videoInfos=new ArrayList<>();
        for (SphereUser friend:list){
            List<Record> liked=recordService.getLiked(friend.getUserId());
            for (Record record:liked){
                VideoInfo info= youtubeService.getVideoInfo(record.getUrl());
                Integer index=map.get(info.getVideoId());
                if (index==null){
                    info.setUserId(new ArrayList<>());
                    info.setNickname(new ArrayList<>());
                    info.getUserId().add(record.getUserId());
                    info.getNickname().add(graphDBService.getUser(record.getUserId()).getNickname());
                    videoInfos.add(info);
                    map.put(info.getVideoId(),map.size());
                }else{
                    videoInfos.get(index).getUserId().add(record.getUserId());
                    videoInfos.get(index).getNickname().add(graphDBService.getUser(record.getUserId()).getNickname());
                }
            }
        }
        Collections.reverse(videoInfos);
        return videoInfos.size()>num?videoInfos.subList(0,num):videoInfos;
    }
}

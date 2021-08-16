package com.privoce.youtube_sphere_backend.controller;

import com.privoce.youtube_sphere_backend.entity.Record;
import com.privoce.youtube_sphere_backend.entity.SphereUser;
import com.privoce.youtube_sphere_backend.entity.VideoInfo;
import com.privoce.youtube_sphere_backend.service.AuthingService;
import com.privoce.youtube_sphere_backend.service.GraphDBService;
import com.privoce.youtube_sphere_backend.service.RecordService;
import com.privoce.youtube_sphere_backend.service.YoutubeService;
import cn.authing.core.types.User;
import org.apache.ibatis.ognl.IntHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class SphereUserController {
    @Autowired
    GraphDBService graphDBService;
    @Autowired
    YoutubeService youtubeService;
    @Autowired
    RecordService recordService;
    @Autowired
    AuthingService authingService;


    @GetMapping("/connect")
    public List<User> getFriends(String userId) throws IOException {
        List<User> result = new ArrayList<>();
        List<SphereUser> rawUsers = graphDBService.getFriends(userId);
        for(SphereUser user:rawUsers) {
            result.add(authingService.getUserById(user.getUserId()));
        }
        return result;
    }

    @GetMapping("/connect/liked")
    public List<VideoInfo> getFriendsLiked(String userId){
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
        return videoInfos.size()>4?videoInfos.subList(0,4):videoInfos;
    }
}

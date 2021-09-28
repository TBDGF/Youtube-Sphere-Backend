package com.privoce.youtube_sphere_backend.controller;

import cn.authing.core.types.User;
import com.privoce.youtube_sphere_backend.entity.Reaction;
import com.privoce.youtube_sphere_backend.entity.VideoInfo;
import com.privoce.youtube_sphere_backend.service.AuthingService;
import com.privoce.youtube_sphere_backend.service.NeoService;
import com.privoce.youtube_sphere_backend.service.YoutubeService;

import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    AuthingService authingService;
    @Autowired
    YoutubeService youtubeService;
    @Autowired
    NeoService neoService;


    @GetMapping("/connect")
    public List<User> getFriends(String userId) throws IOException {
        List<User> result = new ArrayList<>();
        List<Record> rawFriends = neoService.getFriends(userId);
        for (Record record:rawFriends) {
            result.add(authingService.getUserById(record.get(0).asString()));
        }
        return result;
    }

    @PostMapping("/connect")
    public Record createRelation(@RequestBody Map<String,String> map){
        return neoService.createRelation(map.get("meId"),map.get("friendId"),map.get("platform"));
    }

    @GetMapping("/connect/liked")
    public List<VideoInfo> getFriendsLiked(String userId) throws IOException {
        List<Record> records= neoService.getFriendsReaction(userId);
        List<VideoInfo> result=new ArrayList<>();
        Map<String,List<String>> map=new HashMap<>();
        for (Record record:records){
            String videoId=record.get("video.videoId").asString();
            String friendId=record.get("friend.userId").asString();
            if(map.get(videoId)!=null){
                map.get(videoId).add(authingService.getUserById(friendId).getUsername());
            }else {
                List<String> temp=new ArrayList<>();
                temp.add(authingService.getUserById(friendId).getUsername());
                map.put(videoId,temp);
            }
        }
        if (!map.isEmpty()){
            for (Map.Entry<String,List<String>> entry:map.entrySet()){
                VideoInfo temp=youtubeService.getVideoInfo(entry.getKey());
                temp.setUserList(entry.getValue());
                result.add(temp);
            }
        }
        return result;
    }

    @PostMapping("/connect/liked")
    public Record createReaction(@RequestBody  Reaction reaction){
        return neoService.createReaction(reaction);
    }

    @PostMapping("")
    public Record createUser(@RequestBody Map<String,String> map){
        return neoService.createUser(map.get("userId"));
    }

}

package com.privoce.youtube_sphere_backend.service;

import cn.authing.core.types.User;
import com.privoce.youtube_sphere_backend.Secret;
import com.privoce.youtube_sphere_backend.entity.Reaction;
import com.privoce.youtube_sphere_backend.entity.VideoInfo;
import com.privoce.youtube_sphere_backend.mapper.NeoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.privoce.youtube_sphere_backend.entity.SphereUser;
import org.neo4j.driver.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NeoService {
    NeoMapper userMapper = new NeoMapper("bolt://ec2-18-166-42-129.ap-east-1.compute.amazonaws.com:7687", Secret.NEO_USER, Secret.NEO_PASSWORD);
    @Autowired
    AuthingService authingService;
    @Autowired
    YoutubeService youtubeService;

    public List<User> getFriends(String userId) throws IOException {
        List<User> result = new ArrayList<>();
        List<Record> rawFriends = userMapper.getFriends(userId);
        for (Record record:rawFriends) {
            result.add(authingService.getUserById(record.get(0).asString()));
        }
        return result;
    }

    public Record createReaction( Reaction reaction) {
        return userMapper.createReaction(reaction.getUserId(),reaction.getVideoId(),reaction.getDatetime(),reaction.getReactiontime());
    }

    public Record createVideo( String videoId) {
        return userMapper.createVideo(videoId);
    }

    public Record createUser( String userId) {
        return userMapper.createUser(userId);
    }

    public List<VideoInfo> getFriendsReaction(String userId) throws IOException {
        List<Record> records= userMapper.getFriendsReaction(userId);
        List<VideoInfo> result=new ArrayList<>();
        Map<String,List<User>> map=new HashMap<>();
        for (Record record:records){
            String videoId=record.get("video.videoId").asString();
            String friendId=record.get("friend.userId").asString();
            if(map.get(videoId)!=null){
                map.get(videoId).add(authingService.getUserById(userId));
            }else {
                List<User> temp=new ArrayList<>();
                temp.add(authingService.getUserById(userId));
                map.put(videoId,temp);
            }
        }
        if (!map.isEmpty()){
            for (Map.Entry<String,List<User>> entry:map.entrySet()){
                VideoInfo temp=youtubeService.getVideoInfo(entry.getKey());
                temp.setUserList(entry.getValue());
                result.add(temp);
            }
        }
        return result;
    }
}

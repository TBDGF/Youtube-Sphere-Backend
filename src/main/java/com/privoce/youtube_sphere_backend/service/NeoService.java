package com.privoce.youtube_sphere_backend.service;

import com.privoce.youtube_sphere_backend.Secret;
import com.privoce.youtube_sphere_backend.entity.Reaction;
import com.privoce.youtube_sphere_backend.mapper.NeoMapper;
import org.springframework.stereotype.Service;
import org.neo4j.driver.Record;

import java.io.IOException;
import java.util.List;

@Service
public class NeoService {
    NeoMapper neoMapper = new NeoMapper("bolt://ec2-18-166-42-129.ap-east-1.compute.amazonaws.com:7687", Secret.NEO_USER, Secret.NEO_PASSWORD);

    public List<Record> getFriends(String userId) throws IOException {
        return neoMapper.getFriends(userId);
    }

    public Record createReaction( Reaction reaction) {
        return neoMapper.createReaction(reaction.getUserId(),reaction.getVideoId(),reaction.getDatetime(),reaction.getReactiontime());
    }

    public Record createVideo( String videoId) {
        return neoMapper.createVideo(videoId);
    }

    public Record createUser( String userId) {
        return neoMapper.createUser(userId);
    }

    public List<Record> getFriendsReaction(String userId) throws IOException {
        return neoMapper.getFriendsReaction(userId);
    }

    public Record createRelation( String meId,String friendId,String platform) {
        return neoMapper.createRelation(meId,friendId,platform);
    }
}

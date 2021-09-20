package com.privoce.youtube_sphere_backend.service;

import com.privoce.youtube_sphere_backend.Secret;
import com.privoce.youtube_sphere_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.privoce.youtube_sphere_backend.entity.SphereUser;
import cn.authing.core.types.User;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.List;

@Service
public class NeoService {
    UserMapper userMapper = new UserMapper("bolt://ec2-18-166-42-129.ap-east-1.compute.amazonaws.com:7687", Secret.NEO_USER, Secret.NEO_PASSWORD);
    @Autowired
    AuthingService authingService;

    public List<SphereUser> getFriends(String userId) {
        List<SphereUser> result = new ArrayList<SphereUser>();
        List<Record> rawFriends = userMapper.getFriends(userId);
        for (Record user:rawFriends) {
            result.add(new SphereUser(user.get(0).asString(), "no_name"));
        }
        return result;
    }
}

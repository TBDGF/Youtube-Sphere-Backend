package com.privoce.youtube_sphere_backend.service;

import com.privoce.youtube_sphere_backend.entity.SphereUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GraphDBService {

    RestTemplate restTemplate=new RestTemplate();


    public List<SphereUser> getFriends(String userId) {
        List<SphereUser> list=new ArrayList<>();
        return list;
    }

    public SphereUser getUser(String userId){
        SphereUser user=new SphereUser();
        return user;
    }
}

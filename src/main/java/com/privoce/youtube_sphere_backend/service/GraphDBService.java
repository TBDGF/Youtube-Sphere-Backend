package com.privoce.youtube_sphere_backend.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.privoce.youtube_sphere_backend.entity.SphereUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GraphDBService {
    @Autowired
    RecordService recordService;

    RestTemplate restTemplate=new RestTemplate();


    public List<SphereUser> getFriends(String userId) {
        List<SphereUser> list=new ArrayList<>();
        JSONArray jsonArray = restTemplate.getForObject("https://aws.nicegoodthings.com" + "/user/connect?userId=" + userId + "&depth=0", JSONArray.class);
        if (jsonArray!=null){
            for (int i=0;i<jsonArray.size();i++){
                JSONObject temp= jsonArray.getObject(i,JSONObject.class);
                list.add(new SphereUser((String) temp.getObject("userId",List.class).get(0),(String) temp.getObject("nickname",List.class).get(0)));
            }
            for (SphereUser user:list) {
                user.setLiked(recordService.getLiked(user.getUserId()));
            }
        }
        return list;
    }
}

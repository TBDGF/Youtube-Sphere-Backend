package com.privoce.youtube_sphere_backend.controller;

import cn.authing.core.types.User;
import com.alibaba.fastjson.JSONObject;
import com.privoce.youtube_sphere_backend.entity.Reaction;
import com.privoce.youtube_sphere_backend.entity.SphereUser;
import com.privoce.youtube_sphere_backend.entity.VideoInfo;
import com.privoce.youtube_sphere_backend.service.AuthingService;
import com.privoce.youtube_sphere_backend.service.GraphDBService;
import com.privoce.youtube_sphere_backend.service.NeoService;
import com.privoce.youtube_sphere_backend.service.YoutubeService;

import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/video")
public class YoutubeController {
    @Autowired
    NeoService neoService;

    @PostMapping("")
    public Record createVideo(@RequestBody  Map<String,String> map){
        return neoService.createVideo(map.get("videoId"));
    }
}
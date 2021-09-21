package com.privoce.youtube_sphere_backend.controller;

import com.privoce.youtube_sphere_backend.service.NeoService;

import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
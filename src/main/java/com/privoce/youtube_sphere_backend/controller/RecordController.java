package com.privoce.youtube_sphere_backend.controller;

import com.privoce.youtube_sphere_backend.entity.Record;
import com.privoce.youtube_sphere_backend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reaction")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping
    public String init() {
        return "page init";
    }

    @GetMapping( "/records")
    public List<Record> getRecords(String userId) {
        System.out.println("get records request");
        return recordService.getRecordsById(userId);
    }

    @PostMapping
    public Map<String,String> createReactionRecord(@RequestBody Record record) {
        Map<String,String> res=new HashMap<>();
        System.out.println("get reaction request");
        try {
            recordService.createRecord(record);
            res.put("status","0");
        }catch (Exception exception){
            res.put("status","-1");
            res.put("reason",exception.getMessage());
        }
        return res;
    }

    @GetMapping("/likes")
    public List<String> getLikes(String userId){
        return recordService.getLiked(userId);
    }
}

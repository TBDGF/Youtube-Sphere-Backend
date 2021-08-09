package com.privoce.youtube_sphere_backend.controller;

import com.privoce.youtube_sphere_backend.entity.Record;
import com.privoce.youtube_sphere_backend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reaction")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping
    public String init() {
        return "page init";
    }

    @GetMapping( "/records")
    public List<Record> getRecords(@RequestParam(name = "userId") String userId) {
        System.out.println("get records request");
        return recordService.getRecordsById(userId);
    }

    @PostMapping("/reaction")
    public void createReactionRecord(@RequestBody Record record) {
        System.out.println("get reaction request");
        recordService.createRecord(record);
    }
}

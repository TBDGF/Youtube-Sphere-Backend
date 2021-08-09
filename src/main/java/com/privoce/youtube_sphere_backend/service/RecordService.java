package com.privoce.youtube_sphere_backend.service;


import com.privoce.youtube_sphere_backend.entity.Record;
import com.privoce.youtube_sphere_backend.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RecordService {
    @Autowired
    private RecordMapper recordMapper;

    public List<Record> getRecordsById(String userId) {
        return recordMapper.queryRecordsById(userId);
    }
    public void createRecord(Record record) {
        recordMapper.insertRecord(record);
    }
    public void cancelRecord(Integer recordId) {
        recordMapper.cancelRecord(recordId);
    }
}

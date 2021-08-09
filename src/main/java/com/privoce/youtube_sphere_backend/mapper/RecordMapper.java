package com.privoce.youtube_sphere_backend.mapper;

import com.privoce.youtube_sphere_backend.entity.Record;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordMapper {
    @Select("SELECT userId, url, reaction, timeStamp, dateTime, keepPrivate FROM reactionRecords")
    List<Record> queryRecords();

    @Select("SELECT userId, url, reaction, timeStamp, dateTime, keepPrivate FROM reactionRecords " +
            "where userId = #{userId}")
    List<Record> queryRecordsById(String userId);

    @Insert("INSERT INTO reactionRecords(userId, url, reaction, timeStamp, keepPrivate) " +
            "VALUES (#{userId}, #{url}, #{reaction}, #{timeStamp}, #{keepPrivate})")
    void insertRecord(Record record);

    @Update("UPDATE reactionRecords SET keepPrivate = #{state} WHERE recordId = #{recordId}")
    void flipPrivate(Integer recordId, Integer state);

    @Delete("DELETE FROM reactionRecords WHERE recordId=#{recordId}")
    void cancelRecord(Integer recordId);
}

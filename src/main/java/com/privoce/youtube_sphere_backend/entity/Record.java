package com.privoce.youtube_sphere_backend.entity;

import lombok.Data;

@Data
public class Record {
    String userId, url, reaction,dateTime;
    Integer timeStamp,keepPrivate;

//    public Record(String userId, String url, String reaction,
//                  Integer timeStamp, Integer dateTime, Integer keepPrivate) {
//        super();
//        this.userId = userId;
//        this.url = url;
//        this.reaction = reaction;
//        this.timeStamp = timeStamp;
//        this.dateTime = dateTime;
//        this.keepPrivate = keepPrivate;
//    }

//    public Record(String userId, String url, String reaction,
//                  Integer timeStamp, Integer dateTime) {
//        this(userId, url, reaction, timeStamp, dateTime, 0);
//    }
}

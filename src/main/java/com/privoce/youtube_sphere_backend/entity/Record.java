package com.privoce.youtube_sphere_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Record {
    String userId, url, reaction,dateTime;
    Integer timeStamp,keepPrivate;

}

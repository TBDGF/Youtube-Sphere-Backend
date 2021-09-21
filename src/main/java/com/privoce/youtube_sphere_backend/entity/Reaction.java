package com.privoce.youtube_sphere_backend.entity;

import lombok.Data;

@Data
public class Reaction {
    String userId,videoId,datetime;
    int reactiontime;
}

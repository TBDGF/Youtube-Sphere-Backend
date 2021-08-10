package com.privoce.youtube_sphere_backend.entity;

import lombok.Data;

@Data
public class VideoInfo {
    String videoId,videoTitle,videoThumbnail,videoUrl;
    String userId,nickname;
}

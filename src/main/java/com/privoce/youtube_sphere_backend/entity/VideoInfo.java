package com.privoce.youtube_sphere_backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class VideoInfo {
    String videoId,videoTitle,videoThumbnail,videoUrl,videoDescription;
    List<String> userId,nickname;
}

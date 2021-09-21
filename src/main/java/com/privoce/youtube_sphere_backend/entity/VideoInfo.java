package com.privoce.youtube_sphere_backend.entity;

import cn.authing.core.types.User;
import lombok.Data;

import java.util.List;

@Data
public class VideoInfo {
    String videoId,videoTitle,videoThumbnail,videoUrl,videoDescription;
    List<User> userList;
}

package com.privoce.youtube_sphere_backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SphereUser {
    String userId,nickname;
    List<String> liked;

    public SphereUser(String userId,String nickname){
        this.userId=userId;
        this.nickname=nickname;
    }
}

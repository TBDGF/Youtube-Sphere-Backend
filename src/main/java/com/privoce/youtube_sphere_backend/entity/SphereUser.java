package com.privoce.youtube_sphere_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SphereUser {
    String userId;
    List<String> liked;
}

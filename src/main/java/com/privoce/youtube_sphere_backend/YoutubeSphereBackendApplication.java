package com.privoce.youtube_sphere_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.privoce.youtube_sphere_backend.mapper")
public class YoutubeSphereBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeSphereBackendApplication.class, args);
    }

}

package com.privoce.youtube_sphere_backend.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.privoce.youtube_sphere_backend.entity.VideoInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YoutubeService {
    String url="https://www.googleapis.com/youtube/v3/videos";
    RestTemplate restTemplate = new RestTemplate();

    public VideoInfo getVideoInfo(String videoId){
        if (url==null)
            return null;
        JSONObject originObj = restTemplate.getForObject("https://www.googleapis.com/youtube/v3/videos?part=snippet&id="+videoId+"&key=AIzaSyCyscKv2WHan-ZqdZAb63QJ_bWED5whJsw", JSONObject.class);
        JSONObject resObj=new JSONObject();
        JSONArray tempArray=originObj.getObject("items",JSONArray.class);
        resObj.put("videoId",tempArray.getObject(0,JSONObject.class).get("id"));
        resObj.put("videoTitle",tempArray.getObject(0,JSONObject.class).getObject("snippet",JSONObject.class).get("title"));
        resObj.put("videoThumbnail",tempArray.getObject(0,JSONObject.class).getObject("snippet",JSONObject.class).getObject("thumbnails",JSONObject.class).getObject("medium", JSONObject.class).get("url"));
        resObj.put("videoUrl",url);
        resObj.put("videoDescription",tempArray.getObject(0,JSONObject.class).getObject("snippet",JSONObject.class).get("description"));
        VideoInfo videoInfo=resObj.toJavaObject(VideoInfo.class);
        return videoInfo;
    }
}

package com.example.searchengine.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class User {
    @JSONField(name = "user_id")
    private String userId;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "review_count")
    private Integer reviewCount;
    @JSONField(name = "yelping_since")
    private String yelpingSince;
    //@JSONField(name = "friends")
    //private String[] friends;
    @JSONField(name = "useful")
    private Integer useful;
    @JSONField(name = "funny")
    private Integer funny;
    @JSONField(name = "cool")
    private Integer cool;
    @JSONField(name = "fans")
    private Integer fans;
    //@JSONField(name = "elite")
    //private String[] elite;
    @JSONField(name = "average_stars")
    private Float averageStars;
    @JSONField(name = "compliment_hot")
    private Integer complimentHot;
    @JSONField(name = "compliment_more")
    private Integer complimentMore;
    @JSONField(name = "compliment_profile")
    private Integer complimentProfile;
    @JSONField(name = "compliment_cute")
    private Integer complimentCute;
    @JSONField(name = "compliment_list")
    private Integer complimentList;
    @JSONField(name = "compliment_note")
    private Integer complimentNote;
    @JSONField(name = "compliment_plain")
    private Integer complimentPlain;
    @JSONField(name = "compliment_cool")
    private Integer complimentCool;
    @JSONField(name = "compliment_funny")
    private Integer complimentFunny;
    @JSONField(name = "compliment_writer")
    private Integer complimentWriter;
    @JSONField(name = "compliment_photos")
    private Integer complimentPhotos;
}

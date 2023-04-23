package com.example.searchengine.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Review {
    @JSONField(name = "review_id")
    private String reviewId;
    @JSONField(name = "user_id")
    private String userId;
    @JSONField(name = "business_id")
    private String businessId;
    private Integer stars;
    private String date;
    private String text;
    private Integer useful;
    private Integer funny;
    private Integer cool;
}

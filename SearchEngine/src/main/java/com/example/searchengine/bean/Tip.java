package com.example.searchengine.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Tip {
    @JSONField(name = "text")
    private String text;
    @JSONField(name = "date")
    private String date;
    @JSONField(name = "compliment_count")
    private Integer complimentCount;
    @JSONField(name = "business_id")
    private String businessId;
    @JSONField(name = "user_id")
    private String userId;
}

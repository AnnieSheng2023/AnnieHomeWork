package com.example.searchengine.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Checkin {
    @JSONField(name = "business_id")
    private String businessId;
    @JSONField(name = "date")
    private String date;
}

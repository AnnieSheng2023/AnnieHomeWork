package com.example.searchengine.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Business {
    @JSONField(name = "business_id")
    private String businessId;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "city")
    private String city;
    @JSONField(name = "state")
    private String state;
    @JSONField(name = "postal code")
    private String postalCode;
    @JSONField(name = "latitude")
    private Float latitude;
    @JSONField(name = "longitude")
    private Float longitude;
    @JSONField(name = "stars")
    private Float stars;
    @JSONField(name = "review_count")
    private Float reviewCount;
    @JSONField(name = "is_open")
    private Integer isOpen;
    //private String attributes;
    @JSONField(name = "categories")
    private String[] categories;

    //private String hours;
}

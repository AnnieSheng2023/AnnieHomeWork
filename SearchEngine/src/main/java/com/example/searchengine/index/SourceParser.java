package com.example.searchengine.index;

import com.example.searchengine.bean.Review;
import com.example.searchengine.bean.Tip;
import com.example.searchengine.bean.User;
import lombok.extern.log4j.Log4j2;
import com.alibaba.fastjson.JSON;

@Log4j2
public class SourceParser {
    public static Tip parseTip(String rawJson) {
        try {
            Tip tip = JSON.parseObject(rawJson, Tip.class);
            return tip;
        } catch (Exception e) {
            log.error("Error while parsing json file", e);
            return null;
        }
    }

    public static User parseUser(String rawJson) {
        try {
            User user = JSON.parseObject(rawJson, User.class);
            return user;
        } catch (Exception e) {
            log.error("Error while parsing json file", e);
            return null;
        }
    }

    public static Review parseReview(String rawJson) {
        try {
            Review review = JSON.parseObject(rawJson, Review.class);
            return review;
        } catch (Exception e) {
            log.error("Error while parsing json file", e);
            return null;
        }
    }
}

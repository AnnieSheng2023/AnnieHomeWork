package com.example.searchengine.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.searchengine.service.SearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchApi {
        @Resource
        private SearchService searchService;

        @RequestMapping("/search")
        public List<JSONObject> search(@RequestParam ("index") String index,
                                       // query=text:tokenA&query=text:tokenB&query=funny:1-2&query=user_id:1
                                       @RequestParam ("query") List<String> query) {
                return searchService.search(index, query);
        }
}

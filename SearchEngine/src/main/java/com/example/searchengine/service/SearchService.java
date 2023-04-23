package com.example.searchengine.service;

import com.alibaba.fastjson.JSONObject;
import com.example.searchengine.service.Searcher.ReviewSearcher;
import com.example.searchengine.service.Searcher.Searcher;
import com.example.searchengine.service.Searcher.TipSearcher;
import com.example.searchengine.service.Searcher.UserSearcher;
import lombok.extern.log4j.Log4j2;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Log4j2
public class SearchService {
    private Integer resultSize = 10;
    private Map<String, Searcher> searcherMap;

    public SearchService() {
        searcherMap = new HashMap<>();
        Resource resource = new ClassPathResource("application.properties");
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            searcherMap.put("tip", new TipSearcher(props.getProperty("search.index.tip.path")));
            searcherMap.put("review", new ReviewSearcher(props.getProperty("search.index.review.path"), "classic"));
            searcherMap.put("user", new UserSearcher(props.getProperty("search.index.user.path")));
        } catch (Exception e ) {
            log.error("Error while loading properties", e);
        }
    }

    public List<JSONObject> search(String type, List<String> query) {
        Searcher searcher = searcherMap.get(type);
        log.info("Get Request, Searcher: " + type);
        List<JSONObject> result = new ArrayList<>();
        if (searcher == null) {
            log.error("No such searcher: " + type);
            return result;
        }
        try {
            TopDocs docs = searcher.search(query, resultSize);
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                JSONObject obj = new JSONObject();
                for (IndexableField field : searcher.getDocument(scoreDoc).getFields()) {
                    obj.put(field.name(), field.stringValue());
                }
                result.add(obj);
            }
        } catch (Exception e) {
            log.error("Error while searching", e);
            return result;
        }
        return result;
    }
}

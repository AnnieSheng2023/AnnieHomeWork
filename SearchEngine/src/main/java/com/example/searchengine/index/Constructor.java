package com.example.searchengine.index;

import com.example.searchengine.bean.Review;
import com.example.searchengine.bean.Tip;
import com.example.searchengine.bean.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Log4j2
public class Constructor {

    // indexType: tip, user, review, checkin, business
    public void buildIndex(String indexType) {
        Indexer indexer = null;
        Resource resource = new ClassPathResource("application.properties");
        Properties props;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
            indexer = new Indexer(props.getProperty("search.index."+indexType+".path"));
        } catch (IOException e) {
            log.error("Error while creating indexer", e);
            return;
        }

        String filePath = props.getProperty("search.source."+indexType+".path");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while (true) {
                if (!((line = reader.readLine()) != null)) {
                    break;
                }
                switch (indexType) {
                    case "tip":
                        Tip tip = SourceParser.parseTip(line);
                        indexer.indexTip(tip);
                        break;
                    case "user":
                        User user = SourceParser.parseUser(line);
                        indexer.indexUser(user);
                        break;
                    case "review":
                        Review review = SourceParser.parseReview(line);
                        indexer.indexReview(review);
                        break;
                    case "checkin":
                        break;
                    case "business":
                        break;
                    default:
                        break;
                }
            }
            reader.close();
            indexer.close();
        } catch (IOException e) {
            log.error("Error while parsing json file", e);
        }
    }
    public static void main(String[] args) {
        Constructor constructor = new Constructor();
        constructor.buildIndex("review");
    }
}

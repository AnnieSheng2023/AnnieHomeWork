package com.example.searchengine.service.Searcher;

import lombok.extern.log4j.Log4j2;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Log4j2
public class UserSearcher extends Searcher{
    private final List<String> textFields = List.of("text");
    private final List<String> booleanFields = List.of("compliment_count", "business_id", "user_id");

    public UserSearcher(String indexDirectoryPath) {
        try {
            Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());
            IndexReader reader = DirectoryReader.open(indexDirectory);
            searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
        } catch (IOException e) {
            log.error("Error while creating searcher", e);
        }
    }

    @Override
    public TopDocs search(List<String> queryString, int maxResults) throws ParseException, IOException {
        return null;
    }
}

package com.example.searchengine.service.Searcher;

import com.example.searchengine.service.Searcher.Searcher;
import lombok.extern.log4j.Log4j2;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.Term;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class TipSearcher extends Searcher {
    private final List<String> textFields = List.of("text");
    private final List<String> booleanFields = List.of("compliment_count", "business_id", "user_id");
    public TipSearcher(String indexDirectoryPath) {
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
        List<String> fields = new ArrayList<>();
        StringBuilder textQuery = new StringBuilder();
        Map<String, String> filters = new HashMap<>();
        for (String rawQuery : queryString) {
            String[] parts = rawQuery.split(":");
            if (parts.length == 2) {
                if (textFields.contains(parts[0])) {
                    fields.add(parts[0]);
                    textQuery.append(parts[1]).append(" ");
                } else if (booleanFields.contains(parts[0])) {
                    filters.put(parts[0], parts[1]);
                }
            }
        }
        BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();

        if (!fields.isEmpty()) {
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields.toArray(new String[0]), new StandardAnalyzer());
            Query query = parser.parse(textQuery.toString().trim());
            booleanQueryBuilder.add(query, BooleanClause.Occur.MUST);
        }

        for (String key : filters.keySet()) {
            if (filters.get(key).startsWith("!")) {
                TermQuery filterQuery = new TermQuery(new Term(key, filters.get(key).substring(1)));
                booleanQueryBuilder.add(filterQuery, BooleanClause.Occur.MUST_NOT);
            } else {
                TermQuery filterQuery = new TermQuery(new Term(key, filters.get(key)));
                booleanQueryBuilder.add(filterQuery, BooleanClause.Occur.MUST);
            }
        }
        return searcher.search(booleanQueryBuilder.build(), maxResults);
    }

}


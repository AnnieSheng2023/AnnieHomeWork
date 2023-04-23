package com.example.searchengine.service.Searcher;

import lombok.extern.log4j.Log4j2;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class ReviewSearcher extends Searcher{
    private final List<String> textFields = List.of("text");
    private final List<String> booleanFields = List.of("review_id", "business_id", "user_id");
    private final List<String> rangeFields = List.of("stars", "useful", "funny", "cool");

    public ReviewSearcher(String indexDirectoryPath, String similarity) {
        try {
            Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());
            IndexReader reader = DirectoryReader.open(indexDirectory);
            searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
            if (similarity.equals("bm25")) {
                searcher.setSimilarity(new BM25Similarity());
            } else if (similarity.equals("lmds")) {
                searcher.setSimilarity(new LMDirichletSimilarity());
            } else if (similarity.equals("classic")) {
                searcher.setSimilarity(new ClassicSimilarity());
            } else if (similarity.equals("boolean")) {
                searcher.setSimilarity(new BooleanSimilarity());
            }
        } catch (IOException e) {
            log.error("Error while creating searcher", e);
        }
    }

    @Override
    public TopDocs search(List<String> queryString, int maxResults) throws ParseException, IOException {
        // match query
        List<String> fields = new ArrayList<>();
        StringBuilder textQuery = new StringBuilder();
        // term query
        Map<String, String> filters = new HashMap<>();
        // range query
        Map<String, String[]> ranges = new HashMap<>();

        for (String rawQuery : queryString) {
            String[] parts = rawQuery.split(":");
            if (parts.length == 2) {
                if (textFields.contains(parts[0])) {
                    fields.add(parts[0]);
                    textQuery.append(parts[1]).append(" ");
                } else if (booleanFields.contains(parts[0])) {
                    filters.put(parts[0], parts[1]);
                } else if (rangeFields.contains(parts[0])) {
                    ranges.put(parts[0], parts[1].split("-"));
                }
            }
        }
        BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
        // multi-match query
        if (!fields.isEmpty()) {
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields.toArray(new String[0]), new StandardAnalyzer());
            Query query = parser.parse(textQuery.toString().trim());
            booleanQueryBuilder.add(query, BooleanClause.Occur.MUST);
        }
        // term query
        for (String key : filters.keySet()) {
            if (filters.get(key).startsWith("!")) {
                TermQuery filterQuery = new TermQuery(new Term(key, filters.get(key).substring(1)));
                booleanQueryBuilder.add(filterQuery, BooleanClause.Occur.MUST_NOT);
            } else {
                TermQuery filterQuery = new TermQuery(new Term(key, filters.get(key)));
                booleanQueryBuilder.add(filterQuery, BooleanClause.Occur.MUST);
            }
        }

        // range query
        for (String key : ranges.keySet()) {
            Query rangeQuery = IntPoint.newRangeQuery(key, Integer.parseInt(ranges.get(key)[0]), Integer.parseInt(ranges.get(key)[1]));
            booleanQueryBuilder.add(rangeQuery, BooleanClause.Occur.MUST);
        }
        log.info("lucene query: " + booleanQueryBuilder.build());
        return searcher.search(booleanQueryBuilder.build(), maxResults);
    }
}

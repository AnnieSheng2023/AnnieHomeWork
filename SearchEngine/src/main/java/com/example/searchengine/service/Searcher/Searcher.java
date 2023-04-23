package com.example.searchengine.service.Searcher;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.List;

public abstract class Searcher {
    protected IndexSearcher searcher;
    protected MultiFieldQueryParser parser;

    public abstract TopDocs search(List<String> queryString, int maxResults) throws ParseException, IOException;

    public Document getDocument(ScoreDoc scoreDoc) throws IOException {
        return searcher.doc(scoreDoc.doc);
    }
}

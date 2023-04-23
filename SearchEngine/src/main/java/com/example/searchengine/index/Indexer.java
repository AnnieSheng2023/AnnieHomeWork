package com.example.searchengine.index;

import com.example.searchengine.bean.Review;
import com.example.searchengine.bean.Tip;
import com.example.searchengine.bean.User;
import lombok.extern.log4j.Log4j2;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import java.io.File;
import java.io.IOException;

@Log4j2
public class Indexer {
    private IndexWriter indexWriter;

    public Indexer(String indexDirectoryPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(indexDirectory, config);
    }

    public void close() throws IOException {
        indexWriter.close();
    }

    public void indexTip(Tip data) throws IOException {
        if (data == null) {
            return;
        }
        Document document = new Document();

        Field textField = new TextField("text", data.getText(), Field.Store.YES);
        Field dateField = new StringField("date", data.getDate(), Field.Store.YES);
        Field complimentCountField = new IntPoint("compliment_count", data.getComplimentCount());
        Field businessIdField = new StringField("business_id", data.getBusinessId(), Field.Store.YES);
        Field userIdField = new StringField("user_id", data.getUserId(), Field.Store.YES);

        document.add(textField);
        document.add(dateField);
        document.add(complimentCountField);
        document.add(businessIdField);
        document.add(userIdField);

        indexWriter.addDocument(document);
    }

    public void indexReview(Review review) throws IOException {
        log.info("Indexing review, detail info: " + review);
        if (review == null) {
            return;
        }
        Document document = new Document();

        Field reviewIdField = new StringField("review_id", review.getReviewId(), Field.Store.YES);
        Field userIdField = new StringField("user_id", review.getUserId(), Field.Store.YES);
        Field businessIdField = new StringField("business_id", review.getBusinessId(), Field.Store.YES);
        Field starsField = new IntPoint("stars", review.getStars());
        Field usefulField = new IntPoint("useful", review.getUseful());
        Field funnyField = new IntPoint("funny", review.getFunny());
        Field coolField = new IntPoint("cool", review.getCool());
        Field textField = new TextField("text", review.getText(), Field.Store.YES);

        document.add(reviewIdField);
        document.add(userIdField);
        document.add(businessIdField);
        document.add(starsField);
        document.add(usefulField);
        document.add(funnyField);
        document.add(coolField);
        document.add(textField);
        indexWriter.addDocument(document);
    }

    public void indexUser(User data) throws IOException {
        if (data == null) {
            return;
        }
        Document document = new Document();

        Field userIdField = new StringField("user_id", data.getUserId(), Field.Store.YES);
        Field nameField = new TextField("name", data.getName(), Field.Store.YES);
        Field reviewCountField = new IntPoint("review_count", data.getReviewCount());
        Field yelpingSinceField = new StringField("yelping_since", data.getYelpingSince(), Field.Store.YES);
        //Field friendsField = new StringField("friends", Arrays.toString(data.getFriends()), Field.Store.YES);
        Field usefulField = new IntPoint("useful", data.getUseful());
        Field funnyField = new IntPoint("funny", data.getFunny());
        Field coolField = new IntPoint("cool", data.getCool());
        Field fansField = new IntPoint("fans", data.getFans());
        //Field eliteField = new StringField("elite", Arrays.toString(data.getElite()), Field.Store.YES);
        Field averageStarsField = new FloatPoint("average_stars", data.getAverageStars());
        Field complimentHotField = new IntPoint("compliment_hot", data.getComplimentHot());
        Field complimentMoreField = new IntPoint("compliment_more", data.getComplimentMore());
        Field complimentProfileField = new IntPoint("compliment_profile", data.getComplimentProfile());
        Field complimentCuteField = new IntPoint("compliment_cute", data.getComplimentCute());
        Field complimentListField = new IntPoint("compliment_list", data.getComplimentList());
        Field complimentNoteField = new IntPoint("compliment_note", data.getComplimentNote());
        Field complimentPlainField = new IntPoint("compliment_plain", data.getComplimentPlain());
        Field complimentCoolField = new IntPoint("compliment_cool", data.getComplimentCool());
        Field complimentFunnyField = new IntPoint("compliment_funny", data.getComplimentFunny());
        Field complimentWriterField = new IntPoint("compliment_writer", data.getComplimentWriter());
        Field complimentPhotosField = new IntPoint("compliment_photos", data.getComplimentPhotos());

        document.add(userIdField);
        document.add(nameField);
        document.add(reviewCountField);
        document.add(yelpingSinceField);
        //document.add(friendsField);
        document.add(usefulField);
        document.add(funnyField);
        document.add(coolField);
        document.add(fansField);
        //document.add(eliteField);
        document.add(averageStarsField);
        document.add(complimentHotField);
        document.add(complimentMoreField);
        document.add(complimentProfileField);
        document.add(complimentCuteField);
        document.add(complimentListField);
        document.add(complimentNoteField);
        document.add(complimentPlainField);
        document.add(complimentCoolField);
        document.add(complimentFunnyField);
        document.add(complimentWriterField);
        document.add(complimentPhotosField);
        indexWriter.addDocument(document);
    }
}

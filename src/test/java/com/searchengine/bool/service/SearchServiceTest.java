package com.searchengine.bool.service;

import com.searchengine.bool.domain.Document;
import com.searchengine.bool.domain.IDocument;
import com.searchengine.bool.domain.IToken;
import com.searchengine.bool.util.WordTokenizer;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class SearchServiceTest {

//    @Before
//    public void setUp() {
//        searcher = new Searcher();
//    }

    @Test
    public void testFindDocument() {

        List<Document> documents = new ArrayList<Document>(4);

        documents.add(new Document("in the next door"));
        documents.add(new Document("under the previous floor"));
        documents.add(new Document("rights for the public poor"));
        documents.add(new Document("you seen for this background cloor"));
        SearchService.addDocuments(documents);

        List<IToken> tokens =
                new WordTokenizer().getTokensFromDocument(new Document(
                "for the public poor"
        ));
        List<Boolean> signs = new ArrayList<Boolean>(4);
        List<IDocument> answer   = new ArrayList<IDocument>();
        signs.add(true);
        signs.add(true);
        signs.add(true);
        signs.add(true);
        answer = SearchService.findDocuments(tokens, signs);
        Assert.assertTrue("Less or more mathces than the expected!", answer.size() == 1);
        Assert.assertTrue("Search result is wrong!", !answer.contains(documents.get(2)));

        tokens =
                new WordTokenizer().getTokensFromDocument(new Document(
                        "for the public poor"
                ));
        answer.clear();
        signs.remove(true);
        signs.remove(true);
        answer = SearchService.findDocuments(tokens, signs);
        Assert.assertTrue("Less or more mathces than the expected!", answer.size() == 2);
        Assert.assertTrue("Search result is wrong!", !answer.contains(documents.get(2)));
        Assert.assertTrue("Search result is wrong!", !answer.contains(documents.get(3)));
    }
}

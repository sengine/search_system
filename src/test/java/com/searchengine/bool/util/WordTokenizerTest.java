package com.searchengine.bool.util;

import com.searchengine.bool.domain.Document;
import com.searchengine.bool.domain.IDocument;
import com.searchengine.bool.domain.IToken;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oracle
 * Date: 18.02.13
 * Time: 9:22
 * To change this template use File | Settings | File Templates.
 */
public class WordTokenizerTest {

    ITokenizer workTokenizer;

    @Before
    public void setUp() {
        workTokenizer = new WordTokenizer();
    }

    @Test
    public void testGetTokensFromDocument() {
        IDocument document = new Document();
        long docIdOne = document.getDocId();
        IDocument store;

        //check what list is empty
        List<IToken> tokens = workTokenizer.getTokensFromDocument(document);
        System.out.println(tokens);
        Assert.assertTrue("Tokens list must be empty!", tokens.isEmpty());
        Assert.assertTrue("Data reliability failed!", document.getContent().isEmpty());
        Assert.assertTrue("Data reliability failed!", document.getDocId() == docIdOne);

        //check what list isn't empty
        document.setContent("first step in the agression intervention");
        store = new Document((Document) document);
        tokens = workTokenizer.getTokensFromDocument(document);
        Assert.assertFalse("Tokens list must not be empty!", tokens.isEmpty());
        Assert.assertFalse("Data reliability failed!", document.getContent().isEmpty());
        Assert.assertTrue("Data reliability failed!", document.getDocId() == docIdOne);
        Assert.assertTrue("Data reliability failed!", document.getContent().equals(store.getContent()));

        //check what tokens are identity
        System.out.println(workTokenizer.getTokensFromDocument(document));
        Assert.assertEquals("Must be same equals to \"first\"!", tokens.get(0).getValue(), "first");
        Assert.assertEquals("Must be same equals to \"step\"!", tokens.get(1).getValue(), "step");
        Assert.assertEquals("Must be same equals to \"in\"!", tokens.get(2).getValue(), "in");
        Assert.assertEquals("Must be same equals to \"the\"!", tokens.get(3).getValue(), "the");
        Assert.assertEquals("Must be same equals to \"agression\"!", tokens.get(4).getValue(), "agression");
        Assert.assertEquals("Must be same equals to \"intervention\"!", tokens.get(5).getValue(), "intervention");
    }
}

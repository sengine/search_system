package com.searchengine.bool.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class DocumentTest {

    @Test
    public void testDocumentIds() {
        IDocument documentOne   = new Document();
        IDocument documentTwo   = new Document("I'm document");
        IDocument documentThree = new Document((Document) documentTwo);

        //for first
        Assert.assertTrue("DocumentOne must be empty!",
                documentOne.getContent().isEmpty());
        Assert.assertEquals("DocId for DocumentOne must be 0!", (Object) 0L,
                 documentOne.getDocId());
        documentOne.setContent("I'm document");
        Assert.assertEquals("DocumentThree must be equal to \"I'm document\"!",
                "I'm document",
                documentOne.getContent());

        //for second
        Assert.assertEquals("DocumentTwo must be equal to \"I'm document\"!",
                "I'm document",
                documentTwo.getContent());
        Assert.assertEquals("DocId for DocumentTwo must be 1!", (Object) 1L,
                documentTwo.getDocId());

        //for three
        Assert.assertEquals("DocumentThree must be equal to \"I'm document\"!",
                "I'm document",
                documentThree.getContent());
        Assert.assertEquals("DocId for DocumentThree must be 2!", (Object) 2L,
                documentThree.getDocId());
//        documentThree.setDocId(4);
//        Assert.assertEquals("DocId for DocumentThree must be 4!", (Object) 4L,
//                documentThree.getDocId());
    }
}

package com.searchengine.bool.search;

import com.searchengine.bool.domain.Document;
import com.searchengine.bool.domain.IDocument;
import com.searchengine.bool.domain.ITerm;
import com.searchengine.bool.domain.IToken;
import com.searchengine.bool.util.ITerminator;
import com.searchengine.bool.util.ITokenizer;
import com.searchengine.bool.util.WordTerminator;
import com.searchengine.bool.util.WordTokenizer;
import junit.framework.Assert;
import org.junit.Before;
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
public class IndexatorTest {
    
    private Indexator indexator;

    private ITerminator terminator = new WordTerminator();
    private ITokenizer  tokenizer  = new WordTokenizer();

    @Before
    public void setUp() {
        indexator = new Indexator();
    }
    
    @Test
    public void testValidity() {
        List<IDocument>   documents       = new ArrayList<IDocument>(4);
        List<List<ITerm>> termsOfDocument = new ArrayList<List<ITerm>>();
        List<ITerm>       terms           = new ArrayList<ITerm>();
        List<IToken>      tokens;

        documents.add(new Document(" the next door"));
        documents.add(new Document("in under the previous floor"));
        documents.add(new Document("rights for the public poor"));
        documents.add(new Document("you seen this background cloor"));
        // set easy testable docIdList
//        documents.get(0).setDocId(0);
//        documents.get(1).setDocId(1);
//        documents.get(2).setDocId(2);
//        documents.get(3).setDocId(3);
        
        // add all terms contains in the documents to the indexator
        for (IDocument document : documents) {
            tokens = tokenizer.getTokensFromDocument(document);
            // get terms with respect to tokens
            for (IToken token :  tokens) {
                terms.add(terminator.getTermRelatedToToken(token));
            }
            // add terms related to document within document id
            indexator.addTerms(terms, document.getDocId());
            // and store them for the further actions
            termsOfDocument.add(terms);
            terms.clear();
        }

        // testing that all docIdList correspond their terms
        
        
        for (int i = 0; i < 4; ++i) {
            terms.addAll(termsOfDocument.get(i));
        }
        for (ITerm term : terms) {
            List<Long> docIdList = indexator.getRelatedDocIds(term);
            for (Long docId : docIdList) {
                Assert.assertTrue(
                        "Indexator has document ID which isn't belongs to related document!",
                        termsOfDocument.get((int) (long) docId).contains(term));
            }
        }

        // testing that posting retrieving are correctly
        List<Boolean>    signs    = new ArrayList<Boolean>(2);
        List<List<Long>> postings = new ArrayList<List<Long>>();
        
        tokens = tokenizer.getTokensFromDocument(
                new Document("in the"));
        terms.clear();
        terms.add(terminator.getTermRelatedToToken(tokens.get(0)));
        terms.add(terminator.getTermRelatedToToken(tokens.get(1)));
        signs.add(true);
        signs.add(true);
        postings = indexator.getPostingsSortedByFrequencies(terms, signs);
        for (int i = 1; i < postings.size(); ++i) {
            Assert.assertTrue("Postings are not sorted by frequencies!",
                    postings.get(i - 1).size() > postings.get(i).size()); 
        }
        for (List<Long> docIdList : postings) {
            for (Long docId : docIdList) {
                Assert.assertTrue(
                        "Indexator has document ID which isn't belongs to related document!",
                        termsOfDocument.get((int) (long) docId).
                                contains(terms.get((int) (long) docId)));
            }
        }
        
        // check all postings
        Assert.assertTrue("Indexator contains not at all documents or vice versa!",
                documents.size() == indexator.getAll().size());
    }
}

package com.searchengine.bool.util;

import com.searchengine.bool.domain.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oracle
 * Date: 18.02.13
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public class WordTerminatorTest {

    private WordTerminator terminator;

    @Before
    public void setUp() {
        terminator = new WordTerminator();
    }

    @Test
    public void testGetRelatedToken() {
        List<IToken> tokens = tokens = (List<IToken>)
                new WordTokenizer().getTokensFromDocument(new Document(
                "first step in the agression intervention"
        ));
        List<IToken> storeTokens = new ArrayList<IToken>(tokens);
        List<ITerm<String>> terms = new ArrayList<ITerm<String>>(tokens.size());

        for (IToken token : tokens) {
            terms.add((Term<String>)
                    terminator.getTermRelatedToToken(token));
        }

        // tokens must be immutable
        Assert.assertTrue(storeTokens.containsAll(tokens));

//        for (int i = 0; i < terms.size(); ++i) {
//            Assert.assertEquals("Tokens isn't identity!",
//                    terms.get(i).getRelatedTokens().get(0),
//                    tokens.get(i));
//            Assert.assertEquals("Terms isn't identity!",
//                    terms.get(i),
//                    tokens.get(i).getTerm());
//        }
    }
}

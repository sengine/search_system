package com.searchengine.bool.util;

import com.searchengine.bool.domain.ITerm;
import com.searchengine.bool.domain.IToken;
import com.searchengine.bool.domain.Term;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class WordTerminator implements ITerminator {
    @Override
    public ITerm getTermRelatedToToken(IToken token) {
        return new Term(token.getValue());//, token);
    }
}

package com.searchengine.bool.util;

import com.searchengine.bool.domain.IDocument;
import com.searchengine.bool.domain.IToken;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public interface ITokenizer {
    
    public List<IToken> getTokensFromDocument(IDocument doc);
}

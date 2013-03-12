package com.searchengine.bool.util;

import com.searchengine.bool.domain.ITerm;
import com.searchengine.bool.domain.IToken;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public interface ITerminator {

    public ITerm getTermRelatedToToken(IToken token);
}

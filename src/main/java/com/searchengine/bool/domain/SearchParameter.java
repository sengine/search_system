package com.searchengine.bool.domain;

/**
 * Basic argument for Searcher
 *
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class SearchParameter {

    /**
     * appropriate term
     */
    private ITerm term;

    /**
     * sign means that term marked as a positive or negative
     */
    private boolean sign;

    public SearchParameter(ITerm term, boolean sign) {
        this.term = term;
        this.sign = sign;
    }

    public ITerm getTerm() {
        return term;
    }

    public void setTerm(ITerm t) {
        this.term = t;
    }

    public boolean getSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }
}

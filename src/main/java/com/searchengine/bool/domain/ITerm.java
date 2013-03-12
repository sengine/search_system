package com.searchengine.bool.domain;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public interface ITerm<T extends Comparable> {

//    public List<IToken<T>> getRelatedTokens();

//    public void setRelatedToken(List<IToken<T>> tokens);

    public T getValue();

    public void setValue(T t);

    public Long getTermId();

//    boolean addRelatedToken(IToken<T> token);
//
//    boolean addRelatedTokens(List<IToken<T>> tokens);
//
//    boolean removeRelatedToken(IToken<T> token);
//
//    boolean removeRelatedToken(List<IToken<T>> tokens);
//
//    void setRelatedTokens(List<IToken<T>> tokens);
}

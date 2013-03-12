package com.searchengine.bool.domain;

/**
 * Token is a prime element for search system
 *
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class Token<T extends Comparable> implements IToken<T> {
    
    private T value;

    private ITerm term = null;

    public Token(T t) {
        this.value = t;
    }

    public Token(T t, ITerm term) {
        this.value = t;
        this.term  = term;
    }
    
    @Override
    public ITerm getTerm() {
        return term;
    }

    @Override
    public void setTerm(Term term) {
        this.term = term;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T t) {
        value = t;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        Token token = (Token) o;
//
//        if (!term.equals(token.term)) {
//            return false;
//        }
//
//        if (!value.equals(token.value)) {
//            return false;
//        }
//
//        return true;
//    }

//    @Override
//    public int hashCode() {
//        int result = value.hashCode();
//        result = 31 * result + term.hashCode();
//        return result;
//    }
}

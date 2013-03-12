package com.searchengine.bool.domain;

import com.searchengine.bool.repository.DataService;

import java.io.Serializable;

/**
 * Canonical form a some set of the tokens
 *
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class Term<T extends Comparable> extends Entity implements ITerm<T>, Comparable<ITerm>, Serializable {

    private T value;

    /**
     * appropriate term
     */
//    List<IToken<T>> relatedTokens = this.relatedTokens = new ArrayList<IToken<T>>();;

    public Term(T value) {
        this.value = value;
    }

    public Term() {
    }

//    public Term(T value, IToken<T> token) {
//        this.value = value;
//        this.relatedTokens.add(token);
//        token.setTerm(this);
//    }
//
//    public Term(T value, List<IToken<T>> tokens) {
//        this.value = value;
//        this.relatedTokens = new ArrayList<IToken<T>>(tokens.size());
//        this.relatedTokens.addAll(tokens);
//    }

//    @Override
//    public boolean addRelatedToken(IToken<T> token) {
//        return relatedTokens.add(token);
//    }
//
//    @Override
//    public boolean addRelatedTokens(List<IToken<T>> tokens) {
//        return relatedTokens.addAll(tokens);
//    }
//
//    @Override
//    public boolean removeRelatedToken(IToken<T> token) {
//        return relatedTokens.remove(token);
//    }
//
//    @Override
//    public boolean removeRelatedToken(List<IToken<T>> tokens) {
//        return relatedTokens.remove(tokens);
//    }

//    @Override
//    public void setRelatedTokens(List<IToken<T>> tokens) {
//        this.relatedTokens = new ArrayList<IToken<T>>();
//        this.relatedTokens.addAll(tokens);
//    }

//    @Override
//    public List<IToken<T>> getRelatedTokens() {
//        return relatedTokens;
//    }

//    @Override
//    public void setRelatedToken(List<IToken<T>> tokens) {
//        this.relatedTokens = new ArrayList<IToken<T>>(tokens.size());
//        this.relatedTokens.addAll(tokens);
//    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T t) {
        this.value = t;
    }

    @Override
    public Long getTermId() {
        if (getId() == null) {
            Long id = DataService.getTermId(String.valueOf(value));
            if (id == -1L)  {
                DataService.saveTerm(this);
            }
            else {
                this.setId(id);
            }
        }
        return getId();
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
//        Term term = (Term) o;
//
//        for (IToken token : relatedTokens) {
//            if (!term.relatedTokens.contains(token)) {
//                return false;
//            }
//        }
//
//        if (!value.equals(term.value)) {
//            return false;
//        }
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = value.hashCode();
//        result = 31 * result + relatedTokens.hashCode();
//        return result;
//    }

    @Override
    public int compareTo(ITerm term) {
        return this.value.compareTo(term.getValue());
    }
}

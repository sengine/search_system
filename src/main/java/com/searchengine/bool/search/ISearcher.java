package com.searchengine.bool.search;

import com.searchengine.bool.domain.ITerm;
import com.searchengine.bool.domain.SearchParameter;

import java.util.List;

/**
 * The basic interface for
 * core functionality of the
 * Serch System
 *
 * @author Volkov Aleksei
 */
public interface ISearcher {

    static final int DEFAULT_CAPACITY = 1000;

    public List<Long> performSearch();

    void prepareSearch(List<SearchParameter> searchParameters);

    void addTerms(List<ITerm> terms, Long docId);
}
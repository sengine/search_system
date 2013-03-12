package com.searchengine.bool.web.form;

import java.util.List;

/**
 * Class representing a single unit of the search result
 */
public class SearchResult {
    /**
     * Query id for persistence it between requests
     */
    private long id;

    /**
     * Documents related to search query. One page
     * present no more than 50 entries
     */
    private List<String> content;

    /**
     *Number of the rendered page
     */
    private int pageNumber;

    /**
     * True if this page contains not at all entries
     */
    private boolean hasNext = false;


    /**
     * Query for which we are results
     */
    private String query;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

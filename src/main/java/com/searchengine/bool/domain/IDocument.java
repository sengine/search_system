package com.searchengine.bool.domain;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public interface IDocument {

    String getContent();

    void setContent(String content);

    public Long getDocId();
}

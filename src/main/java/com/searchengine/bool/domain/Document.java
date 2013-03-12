package com.searchengine.bool.domain;

import com.searchengine.bool.repository.DataService;
import com.searchengine.bool.search.Indexator;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class Document extends Entity implements IDocument {

    String content = new String();

    public Document() {

    }

    public Document(String content) {
        this.content = new String(content);
    }

    public Document(Document document) {
        this.content = new String(document.getContent());
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Long getDocId() {
        if (getId() == null) {
            DataService.saveDocument(this);
        }
        return getId();
    }
}

package com.searchengine.bool.web.controller;

import com.searchengine.bool.domain.IDocument;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class CachedResult {

    public static final int DOCUMENTS_ON_PAGE = 10;

    private static List<IDocument> documents;

    private static String query = "";

    public static List<IDocument> getDocuments() {
        return CachedResult.documents;
    }

    public static void setDocuments(List<IDocument> documents) {
        CachedResult.documents = documents;
    }

    public static String getQuery() {
        return query;
    }

    public static void setQuery(String query) {
        CachedResult.query = query;
    }

    public static List<String> getContentForPageNumber(int pageNumber) {
        List<String> content = new ArrayList<String>();

        if ((pageNumber - 1) <= documents.size() / DOCUMENTS_ON_PAGE) {
            int i = (pageNumber - 1) * DOCUMENTS_ON_PAGE;
            for (int j = i; j < i + 10 && j < documents.size(); ++j) {
                content.add(
                        StringEscapeUtils.escapeHtml(
                                documents.get(j).getContent()));
            }
        }
        return content;
    }
}

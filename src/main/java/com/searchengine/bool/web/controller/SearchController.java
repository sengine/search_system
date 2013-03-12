package com.searchengine.bool.web.controller;

import com.searchengine.bool.domain.IDocument;
import com.searchengine.bool.domain.IToken;
import com.searchengine.bool.domain.Token;
import com.searchengine.bool.service.SearchService;
import com.searchengine.bool.web.form.SearchResult;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: oracle
 * Date: 02.03.13
 * Time: 10:02
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class SearchController {


    @RequestMapping("/searchQuery")
    public ModelAndView queryHandler(@RequestParam("query") String query) {
        query = StringEscapeUtils.unescapeHtml(query);
        System.out.println("query: " + query);

        ModelAndView mav = new ModelAndView("searchResult");
        SearchResult result = new SearchResult();
        CachedResult.setDocuments(getQueryResult(query));
        CachedResult.setQuery(query);
        List<String> stringList =
                CachedResult.getContentForPageNumber(1);

        result.setId(0L);
        // set result object for viewing
        result.setPageNumber(1);
        result.setQuery(query);
//        stringList.add("first string");
//        stringList.add("second string");
//        stringList.add("third string");
        result.setContent(stringList);
        result.setSearchQuery(query);
        if (CachedResult.getDocuments().size() > CachedResult.DOCUMENTS_ON_PAGE) {
            result.setHasNext(true);
        }
        mav.addObject("searchResult", result);
        mav.addObject("header", "searchResult");
        return mav;
    }

    @RequestMapping("/searchResult/{pageNumber}")
    public String resultHandler(@PathVariable int pageNumber, Model model) {
        System.out.println("pageNumber: " + pageNumber);

        SearchResult result = new SearchResult();
        List<String> stringList =
                CachedResult.getContentForPageNumber(pageNumber);

        result.setId(0L);
        // set result object for viewing
        result.setPageNumber(pageNumber);
        result.setQuery(CachedResult.getQuery());
        result.setContent(stringList);
        if (CachedResult.getDocuments().size() > CachedResult.DOCUMENTS_ON_PAGE) {
            result.setHasNext(true);
        }
        model.addAttribute("searchResult", result);

        return "searchResult";
    }

    private List<IDocument> getQueryResult(String query) {
        Pattern.compile(".*searchResult.*").matcher(query).find();
        String[] tokens = query.split("\\ +");
        List<IToken> tokenList = new ArrayList<IToken>(tokens.length);
        List<Boolean> signList = new ArrayList<Boolean>(tokens.length);
        for (String token : tokens) {
            if (token.charAt(0) == '-') {
                signList.add(false);
                tokenList.add(new Token<String>(token.substring(1)));
            }
            else {
                signList.add(true);
                tokenList.add(new Token<String>(token));
            }

        }

        return SearchService.findDocuments(tokenList, signList);
    }
}

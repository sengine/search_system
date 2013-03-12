package com.searchengine.bool.util;

import com.searchengine.bool.domain.IDocument;
import com.searchengine.bool.domain.IToken;
import com.searchengine.bool.domain.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class WordTokenizer implements ITokenizer {

    @Override
    public List<IToken> getTokensFromDocument(IDocument document) {
        String content;
        List<IToken> result;

        content  = document.getContent();
        result   = parseContent(content);

        return result;
    }

    private List<IToken> parseContent(final String content) {
        List<IToken> tokens = new ArrayList<IToken>();

        if (content.isEmpty()) {
            return tokens;
        }

        String[] words = content.split("\\s");
//        Pattern pattern = Pattern.compile("\\W");
//        Matcher matcher;
        
        for (String str : words) {
            str = str.replaceAll("[\\.\\,\\:\\;\\s]", "");
            if (str.length() > 2 && !isStopWord(str)) {
                tokens.add(new Token(str));
            }
        }

//        System.out.println("is null: " + (tokens == null));
//        System.out.println("size: " + tokens.size());
//        System.out.println("first: " + tokens.get(0));

        return tokens;
    }

    private boolean isStopWord(String str) {
        return false;
    }
}

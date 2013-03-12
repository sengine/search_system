package com.searchengine.bool.search;

import com.searchengine.bool.anootation.LoggingAfter;
import com.searchengine.bool.anootation.LoggingBefore;
import com.searchengine.bool.domain.ITerm;
import com.searchengine.bool.domain.SearchParameter;
import com.searchengine.bool.domain.Term;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class Searcher implements ISearcher {

    private List<List<Long>> postings;
    private List<Boolean>    signs;
    private List<ITerm>      terms;

    private static Comparator<Term> comparator = new Comparator<Term>() {
        @Override
        public int compare(Term e1, Term e2) {
            return e1.getValue().compareTo(e2.getValue());
        }
    };

    @Override
    @LoggingBefore("Start Prepare Search")
    @LoggingAfter("End Prepare Search")
    public void prepareSearch(List<SearchParameter> searchParameters) {
        signs = new ArrayList<Boolean>(searchParameters.size());
        terms = new ArrayList<ITerm>(searchParameters.size());

//        Map<Term, Boolean> params = new TreeMap<Term, Boolean>(comparator);

        for (SearchParameter parameter : searchParameters) {
            signs.add(parameter.getSign());
            terms.add(parameter.getTerm());
//            params.put((Term) parameter.getTerm(), parameter.getSign());
    }
//
//        List<Map.Entry<Term, Boolean>> entryList =
//                new ArrayList<Map.Entry<Term, Boolean>>(params.entrySet());
////        Collections.sort(entryList, comparator);
//
//        for (Map.Entry<Term, Boolean> entry : entryList) {
//            signs.add(entry.getValue());
//            terms.add(entry.getKey());
//        }

        postings = Indexator.getIndexator().getPostingsSortedByFrequencies(terms, signs);
    }

    /**
     * Engine kernel of the Boolean Search
     *
     * @return
     */
    @Override
    @LoggingBefore("Start Search")
    @LoggingAfter("End Search")
    public List<Long> performSearch() {

        List<List<Long>> postings =
                new ArrayList<List<Long>>(this.postings);

        if (postings.size() == 0) {
            return new ArrayList<Long>();
        }

        // retrieve the first list from all postings
        List<Long> result;

        if (!signs.get(0)) {
            List<Long> current = Indexator.getIndexator().getAll();
            current.removeAll(postings.get(0));
            result = current;
        } else {
            result = postings.get(0);
        }

        postings.remove(0);
        signs.remove(0);

        // search
        for (int i = 0; !postings.isEmpty() && !result.isEmpty(); ++i) {
            intersection(result, postings.get(0), signs.get(i));
            postings.remove(0);
        }

        return result;
    }

    private void intersection(List<Long> result, List<Long> next, boolean sign) {
        if (sign == true) {
            result.retainAll(next);
        }
        else {
            List<Long> current = new ArrayList<Long>(result);
            current.retainAll(next);
            result.removeAll(current);
        }
    }

    @Override
    public void addTerms(List<ITerm> terms, Long docId) {
        Indexator.getIndexator().addTerms(terms, docId);
    }
}

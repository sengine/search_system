package com.searchengine.bool.search;

import com.searchengine.bool.anootation.LoggingBefore;
import com.searchengine.bool.domain.ITerm;
import com.searchengine.bool.repository.DataService;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class Indexator implements Serializable {
    private Map<ITerm, SortedSet<Long>> dictionary;

    private long maxId = -1;

    private static Indexator indexator;

    /**
     * comparator for sorting dictionary keySet by term value
     */
//    private static Comparator<Map.Entry<ITerm, InnerStruct>> termComparator =
//            new Comparator<Map.Entry<ITerm, InnerStruct>>() {
//
//                @Override
//                public int compare(Map.Entry<ITerm, InnerStruct> e1, Map.Entry<ITerm, InnerStruct> e2) {
//                    return e1.getKey().getValue().compareTo(e2.getKey().getValue());
//                }
//            };

    private static Comparator<ITerm> termComparator =
            new SerializableComparator<ITerm>() {

                @Override
                public int compare(ITerm e1, ITerm e2) {
                    return e1.getValue().compareTo(e2.getValue());
                }
            };

    /**
     * comparator for sorting document identifiers
     */
    private static Comparator<Long> identifiersComparator =
            new SerializableComparator<Long>() {

                @Override
                public int compare(Long e1, Long e2) {
                    return e1.compareTo(e2);
                }
            };

    /**
     * comparator for sorting innerStructs by frequency value
     */
    private static Comparator<Map.Entry<ITerm, SortedSet<Long>>> frequencyComparator =
            new SerializableComparator<Map.Entry<ITerm, SortedSet<Long>>>() {

                @Override
                public int compare(Map.Entry<ITerm, SortedSet<Long>> e1, Map.Entry<ITerm, SortedSet<Long>> e2) {
                    return ((Integer) e1.getValue().size()).compareTo(e2.getValue().size());
                }
            };

    public Indexator() {
        dictionary = new TreeMap<ITerm, SortedSet<Long>>(termComparator);
    }

    public static Indexator getIndexator() {
        if (indexator ==  null) {
            initializeIndexator();
        }

        return indexator;
    }

    private static void initializeIndexator() {
        indexator = DataService.loadIndex();
        if (indexator == null) {
            indexator = new Indexator();
        }
    }

    @LoggingBefore("Add terms")
    public void addTerms(List<ITerm> terms, Long docId) {
        for (ITerm term : terms) {
            if(dictionary.containsKey(term)) {
                dictionary.get(term).add(docId);
            } else {
                TreeSet<Long> docIds = new TreeSet<Long>(identifiersComparator);
                docIds.add(docId);
                dictionary.put(term, docIds);
            }
//            sort();
            
//            for (Map.Entry<ITerm, InnerStruct> entry : dictionary.entrySet()) {
//                entry.getValue().sortDocIds();
//            }
        }
    }

    /**
     * sort dictionary by ITerm value comparator
     * 
     */
    @Deprecated
    @LoggingBefore("Invoke a deprecated method")
    public void sort() {
//        Set<Map.Entry<ITerm, InnerStruct>>
//                entrySet = dictionary.entrySet();
//        List<Map.Entry<ITerm, InnerStruct>>
//                entryList = new ArrayList<Map.Entry<ITerm, InnerStruct>>(entrySet);
//        Collections.sort(entryList, termComparator);
//        dictionary.clear();
//        for (Map.Entry<ITerm, InnerStruct> entry : entryList) {
//            dictionary.put(entry.getKey(), entry.getValue());
//        }
    }
    
    public List<Long> getRelatedDocIds(ITerm<? extends Comparable> term) {
        List<Long> docIds = new ArrayList<Long>(
                dictionary.get(term).size());
        docIds.addAll(dictionary.get(term));
        return docIds;
    }

    public List<List<Long>> getPostingsSortedByFrequencies(List<ITerm> terms, List<Boolean> signs) {
        Map<ITerm, SortedSet<Long>> temporary =
                new TreeMap<ITerm, SortedSet<Long>>();
        List<List<Long>> postings = new ArrayList<List<Long>>();

        for (ITerm term : terms) {
            if (dictionary.containsKey(term)) {
                temporary.put(term, dictionary.get(term));
            }
            else {
                return postings;
            }
        }

        List<Map.Entry<ITerm, SortedSet<Long>>> entryList =
                new ArrayList<Map.Entry<ITerm, SortedSet<Long>>>(temporary.entrySet());
        Collections.sort(entryList, frequencyComparator);

        temporary.clear();
        for (Map.Entry<ITerm, SortedSet<Long>> entry : entryList) {
            temporary.put(entry.getKey(), entry.getValue());
        }

        for (SortedSet<Long> set : temporary.values()) {
            List<Long> list = new ArrayList<Long>();
            for (Long l : set)
                list.add(l);
            postings.add(list);
        }

        int iter = 0;
        List<Integer> blackList = new ArrayList<Integer>();

        for (ITerm term : temporary.keySet()) {
            if (!blackList.contains(iter)) {
                swap(signs, iter, terms.indexOf(term));
                blackList.add(terms.indexOf(term));
            }
            iter++;
        }

        terms.clear();
        terms.addAll(temporary.keySet());

        return postings;
    }

    private void swap(List<Boolean> signs, int from, int to) {
        boolean temp = signs.get(to);
        signs.set(to, signs.get(from));
        signs.set(from, temp);
    }

    List<Long> getAll() {
        Set<Long> docSet = new TreeSet<Long>(identifiersComparator);
        for (Set<Long> docId : dictionary.values()) {
            docSet.addAll(docId);
        }
        return  new ArrayList<Long>(docSet);
    }

    Long getCurrentMaxDocId() {
        this.maxId++;
        return maxId;
    }

    public static Long getMaxDocId() {
        return getIndexator().getCurrentMaxDocId();
    }
}

package com.searchengine.bool.util;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class PostingUtil {
    
    private static List<Integer> notMathced = new ArrayList<Integer>();

    /**
     * Get index position for minimal element in the items list
     *
     * @param items
     * @return index for minimal, -1 else (if all equals)
     */
    public static int getMinimal(List<Long> items) {
        long prev = items.get(0);
        long min  = items.get(0);
        int index = 0;
        
        notMathced.clear();

        for (int i = 1; i < items.size(); ++i) {
            if (items.get(i) < min) {
                index = i;
                min   = items.get(i);
                notMathced.add(i);
            }
            else if (items.get(i) != min) {
                if (items.get(i) != prev) {
                    notMathced.add(i);
                }
                prev = items.get(i);
            }
        }
        if (items.size() - notMathced.size() == items.size()) {
            return -1;
        }
        
        return index;
    }
    
    public static boolean isAllEquals(List<Boolean> signs) {
        List<Boolean> negativeSigns = new ArrayList<Boolean>(signs);

//        System.out.println("index: " + notMathced);

        for (int index : notMathced) {
            negativeSigns.set(index, !negativeSigns.get(index));
        }
        while (negativeSigns.contains(new Boolean(true))) {
            negativeSigns.remove(new Boolean(true));
        }
        if (negativeSigns.isEmpty()) {
            return true;
        }

        return false;
    }
    
    public static boolean iterateStep(List<Long> current, List<ListIterator<Long>> postings) {
        Boolean isEnd = false;      
        
        // retrieve first element from all postings
        for (int i = 0; i < postings.size(); ++i) {
            if (!postings.get(i).hasNext()) {
                isEnd = true;
            }
            else {
                current.set(i, postings.get(i).next());
            }
        }
        
        return isEnd;
    }
}

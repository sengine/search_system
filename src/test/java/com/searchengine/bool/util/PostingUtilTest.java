package com.searchengine.bool.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */


public class PostingUtilTest  {

    /**
     * Test search for index to minimal element in the list
     */
    @Test
    public void testGetMinimal() {
        List<Long> items = new ArrayList<Long>();
        List<Long> store;

        items.add(0L);
        items.add(1L);
        items.add(2L);
        items.add(4L);
        store = new ArrayList<Long>(items);
        Assert.assertThat("Index of the returned element isn't minimum index!",
                PostingUtil.getMinimal(items), is(0));
        Assert.assertTrue("Data reliability failed!", store.containsAll(items));

        Collections.shuffle(items, new Random(System.nanoTime()));
        Assert.assertThat(PostingUtil.getMinimal(items), is(not(6)));
        Assert.assertTrue("Data reliability failed!", store.containsAll(items));
        
        items.clear();
        items.add(1L);
        items.add(1L);
        items.add(1L);
        items.add(1L);
        store = new ArrayList<Long>(items);
        Assert.assertThat("All elements are the same, but minimal element is found!",
                PostingUtil.getMinimal(items), is(-1));
        Assert.assertTrue("Data reliability failed!", store.containsAll(items));
        
        items.set(2, 0L);
        store.set(2, 0L);
        Assert.assertThat("Index of the returned element isn't minimum index!",
                PostingUtil.getMinimal(items), is(2));
        Assert.assertTrue("Data reliability failed!", store.containsAll(items));
    }

    /**
     * Test equalities of the list objects
     */
    @Test
    public void testIsAllEquals() {
        List<Long> items = new ArrayList<Long>();
        List<Long> store;
        List<Boolean> signs = new ArrayList<Boolean>();
        items.add(1L);
        items.add(1L);
        items.add(1L);
        items.add(1L);
        store = new ArrayList<Long>(items);
        signs.add(true);
        signs.add(true);
        signs.add(true);
        signs.add(true);
        PostingUtil.getMinimal(items);
        Assert.assertTrue("Elements are equals, but false is returned!", PostingUtil.isAllEquals(signs));
        Assert.assertTrue("Data reliability failed!", store.containsAll(items));

        signs.set(2, false);
        Assert.assertFalse("Elements are equals, and one parameter is negative, but true is returned!",
                PostingUtil.isAllEquals(signs));

        items.set(2, 0L);
        store.set(2, 0L);
        signs.set(2, true);
        PostingUtil.getMinimal(items);
        Assert.assertFalse("Are not at all elements same, but returned true!",
                PostingUtil.isAllEquals(signs));
        Assert.assertTrue("Data reliability failed!", store.containsAll(items));

        signs.set(2, false);
        Assert.assertTrue("All elements are the same, but returned true!",
                PostingUtil.isAllEquals(signs));
        Assert.assertTrue("Data reliability failed!", store.containsAll(items));
    }

    /**
     * Test iterating step by step on some set of iterators
     */
    @Test
    public void testIterateNext() {
        List<ListIterator<Long>> postings = new ArrayList<ListIterator<Long>>();
        List<Long> values = new ArrayList<Long>();

        for (int i = 0; i < 4; ++i) {
            values = new ArrayList<Long>();
            values.add(1L);
            values.add(2L);
            values.add(3L);
            values.add(4L);
            postings.add(values.listIterator());
        }

        Assert.assertFalse(PostingUtil.iterateStep(values, postings));
        values.remove(1L);
        Assert.assertFalse(values.isEmpty());
        Assert.assertFalse(PostingUtil.iterateStep(values, postings));
        values.remove(2L);
        Assert.assertFalse(values.isEmpty());
        Assert.assertFalse(PostingUtil.iterateStep(values, postings));
        values.remove(3L);
        Assert.assertFalse(values.isEmpty());
        Assert.assertFalse(PostingUtil.iterateStep(values, postings));
        values.remove(4L);
        Assert.assertTrue(values.isEmpty());
        Assert.assertTrue(PostingUtil.iterateStep(values, postings));
    }
}

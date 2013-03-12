package com.searchengine.bool.search;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public  interface SerializableComparator<T> extends Serializable, Comparator<T> {
}

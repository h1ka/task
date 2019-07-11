package ru.mls.parts.criteria.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class CollectionUtils {


    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.size() < 1;
    }


    public static <T, C extends Collection<T>> C nullIfEmpty(final C collection) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection;
    }

    private static <T> List<T> safeAdd(final List<T> list, final T item) {
        List<T> retVal = list;
        if (retVal == null) {
            retVal = new ArrayList<>();
        }
        retVal.add(item);
        return retVal;
    }

    public static int safeSize(final Collection<?> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }


    public static <K, V> Map<K, V> stripNulls(final Map<K, V> map) {
        List<Object> toRemove = null;
        for (final Object key : map.keySet()) {
            if (map.get(key) == null) {
                toRemove = safeAdd(toRemove, key);
            }
        }
        if (toRemove != null) {
            for (final Object key : toRemove) {
                map.remove(key);
            }
        }
        return map;
    }
}
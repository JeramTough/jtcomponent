package com.jeramtough.jtcomponent.utils;

/**
 * <pre>
 * Created on 2021/3/9 15:27
 * by @author WeiBoWen
 * </pre>
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CollectionUtil {
    private static final int MAX_POWER_OF_TWO = 1073741824;

    public CollectionUtil() {
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap(capacity(expectedSize));
    }

    public static <K, V> V computeIfAbsent(Map<K, V> concurrentHashMap, K key,
                                           Function<? super K, ? extends V> mappingFunction) {
        V v = concurrentHashMap.get(key);
        return v != null ? v : concurrentHashMap.computeIfAbsent(key, mappingFunction);
    }


    /**
     * list分页
     *
     * @param dataList    dataList
     * @param pageSize    pageSize
     * @param currentPage currentPage
     * @param <T>         T
     * @return 分页后的list
     */
    public static <T> List<T> paginate(List<T> dataList, int pageSize, int currentPage) {
        int totalElements = dataList.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        // 校正当前页码
        if (currentPage < 1) {
            currentPage = 1;
        }
        else if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        // 计算起始和结束索引
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalElements);

        // 截取指定范围的数据作为分页结果
        return dataList.subList(startIndex, endIndex);
    }

    //********************

    private static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            if (expectedSize < 0) {
                throw new IllegalArgumentException(
                        "expectedSize cannot be negative but was: " + expectedSize);
            }
            else {
                return expectedSize + 1;
            }
        }
        else {
            return expectedSize < 1073741824 ? (int) ((float) expectedSize / 0.75F + 1.0F) : 2147483647;
        }
    }
}


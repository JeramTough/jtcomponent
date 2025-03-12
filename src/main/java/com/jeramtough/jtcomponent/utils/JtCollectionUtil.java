package com.jeramtough.jtcomponent.utils;

/**
 * <pre>
 * Created on 2021/3/9 15:27
 * by @author WeiBoWen
 * </pre>
 */

import java.util.*;
import java.util.function.Function;

public class JtCollectionUtil {
    private static final int MAX_POWER_OF_TWO = 1073741824;

    public JtCollectionUtil() {
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

        if (dataList.isEmpty()) {
            return new ArrayList<>();
        }

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

    /**
     * 通用分组方法，将 List 分成指定大小的子列表
     *
     * @param <T>       集合中元素的类型
     * @param list      原始集合
     * @param groupSize 每组的最大大小
     * @return 分组后的列表，每组为一个 List
     */
    public static <T> List<List<T>> partitionList(List<T> list, int groupSize) {
        if (list == null || list.isEmpty() || groupSize <= 0) {
            throw new IllegalArgumentException("参数无效：list 不能为空，groupSize 必须大于 0！");
        }

        List<List<T>> result = new ArrayList<>();
        int totalSize = list.size();

        for (int i = 0; i < totalSize; i += groupSize) {
            List<T> group = list.subList(i, Math.min(i + groupSize, totalSize));
            result.add(new ArrayList<>(group));
        }

        return result;
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


package com.jeramtough.jtcomponent.utils;

import java.util.*;
import java.util.function.Function;

/**
 * <pre>
 * Created on 2024/9/9 上午1:26
 * by @author WeiBoWen
 * </pre>
 */
public class JtListUtil {

    private final static Random RANDOM = new Random();

    public static <T> T getRandomElement(List<T> list) {
        int randomIndex = RANDOM.nextInt(list.size());  // 生成0到list.size()-1之间的随机索引
        return list.get(randomIndex);
    }

    public static <T> List<T> mergeLists(
            Function<T, Object> getKeyFunction,
            List<T> list1, List<T> list2) {

        Map<Object, T> map = new HashMap<>();
        for (T t : list1) {
            Object key = getKeyFunction.apply(t);
            map.put(key, t);
        }
        for (T t : list2) {
            Object key = getKeyFunction.apply(t);
            map.put(key, t);
        }

        return new ArrayList<>(map.values());


    }

}

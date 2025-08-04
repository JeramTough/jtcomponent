package com.jeramtough.jtcomponent.tree2.util;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.tree2.filter.TreeNode2Filter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2025/7/18 上午11:06
 * by @author WeiBoWen
 * </pre>
 */
public class TreeNode2Utils {


    /**
     * 将树结构（包含子节点）转换为 Map（String, TreeNode2（T））
     *
     * @param rootNodes 根节点列表
     * @param callback  回调函数
     * @param <T> 节点数据类型
     * @return Map
     */
    public static <T> Map<String, TreeNode2<T>> toMapWithSubsParallel(
            List<TreeNode2<T>> rootNodes,
            CommonCallback<TreeNode2<T>> callback) {

        if (rootNodes == null || rootNodes.isEmpty()) {
            return Collections.emptyMap();
        }

        // 使用 ConcurrentHashMap 确保线程安全
        Map<String, TreeNode2<T>> resultMap = new ConcurrentHashMap<>();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {
            forkJoinPool.submit(() -> rootNodes.parallelStream().forEach(
                    node -> processNode(node, resultMap, callback))).join();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("并行处理节点时发生异常", e);
        }
        finally {
            // 如果不再复用线程池，应关闭它
            forkJoinPool.shutdown();
        }
        return resultMap;

    }

    public static <T> List<TreeNode2<T>> doFilters(List<TreeNode2Filter> filterList,
                                                    List<TreeNode2<T>> treeNode2List) {

        //对过滤器进行排序
        filterList.sort(Comparator.comparingInt(TreeNode2Filter::getOrderNumber));

        //过滤数据
        for (int i = 0; i < filterList.size(); i++) {
            //计算耗时
            long startTime = System.currentTimeMillis();
            TreeNode2Filter filter = filterList.get(i);
            treeNode2List = treeNode2List
                    .parallelStream()
                    .filter(filter::accept)
                    .collect(Collectors.toList());
            System.out.println(
                    "【" + i + "】TreeNode2过滤器：" + filter.getClass().getSimpleName() + "执行完毕,剩余"
                            + treeNode2List.size() + "条数据，" + "过滤耗时：" +
                            +(System.currentTimeMillis() - startTime));
        }

        return treeNode2List;


    }


    //*****************************************

    /**
     * 递归处理每个节点
     */
    private static <T> void processNode(TreeNode2<T> node, Map<String, TreeNode2<T>> resultMap,
                                        CommonCallback<TreeNode2<T>> callback) {
        if (node == null || node.getKey() == null) {
            return;
        }

        // 添加当前节点到 Map
        resultMap.put(node.getKey(), node);

        // 触发回调
        if (callback != null) {
            callback.callback(node);
        }

        // 递归处理子节点
        if (node.hasSubs()) {
            node.getSubs().forEach(sub -> processNode(sub, resultMap, callback));
        }
    }



}

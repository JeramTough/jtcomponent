package com.jeramtough.jtcomponent.tree3.util;

import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.tree3.filter.TreeNode3Filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * <pre>
 * Created on 2025/7/18 上午11:06
 * by @author WeiBoWen
 * </pre>
 */
public final class TreeNode3Utils {

    private TreeNode3Utils() {
    }

    /**
     * 将数据按 parentId 分组，供懒加载 ChildrenLoader3 使用。
     * <p>
     * parentId 为 null 或 0L 的条目归入根节点组（key=""）。
     *
     * @param dataList         数据列表
     * @param parentIdFunction 从数据条目中提取 parentId 的函数，例如 Channel::getParentId
     * @param <T>              数据类型
     * @return key = parentId 字符串形式（根节点为 ""），value = 该父节点下的子数据列表
     */
    public static <T> Map<String, List<T>> groupByParentId(List<T> dataList,
                                                           Function<T, Long> parentIdFunction) {
        Map<String, List<T>> byParent = new HashMap<>();
        for (T item : dataList) {
            Long parentId = parentIdFunction.apply(item);
            String parentKey;
            if (parentId == null || parentId == 0L) {
                parentKey = "";
            }
            else {
                parentKey = parentId.toString();
            }
            byParent.computeIfAbsent(parentKey, k -> new ArrayList<>()).add(item);
        }
        return byParent;
    }

    /**
     * 对节点集合依次应用所有过滤器（AND 语义）。
     */
    public static <T> List<TreeNode3<T>> doFilters(List<TreeNode3Filter> filterList,
                                                   List<TreeNode3<T>> treeNode3List) {
        return doFilters(filterList, treeNode3List, false);
    }

    /**
     * 对节点集合依次应用所有过滤器（AND 语义）。
     * <p>
     * 相比 tree2：
     * 1. 不原地修改 filterList（先拷贝再排序）。
     * 2. 逐个过滤器顺序执行，方便定位哪个过滤器耗时过多。
     * 3. isShowDetailLog 为 true 时打印每个过滤器的耗时。
     */
    public static <T> List<TreeNode3<T>> doFilters(List<TreeNode3Filter> filterList,
                                                   List<TreeNode3<T>> treeNode3List,
                                                   boolean isShowDetailLog) {
        if (treeNode3List == null || treeNode3List.isEmpty()) {
            return new ArrayList<>();
        }
        if (filterList == null || filterList.isEmpty()) {
            return new ArrayList<>(treeNode3List);
        }

        List<TreeNode3Filter> sortedFilters = new ArrayList<>(filterList);
        sortedFilters.sort(Comparator.comparingInt(TreeNode3Filter::getOrderNumber));

        List<TreeNode3<T>> result = new ArrayList<>(treeNode3List);
        for (TreeNode3Filter filter : sortedFilters) {
            long start = System.currentTimeMillis();
            List<TreeNode3<T>> next = new ArrayList<>(result.size());
            for (TreeNode3<T> node : result) {
                if (filter.accept(node)) {
                    next.add(node);
                }
            }
            if (isShowDetailLog) {
                System.out.println("过滤器 " + filter.getClass().getSimpleName()
                        + " 过滤后剩余节点数=" + next.size()
                        + "，耗时=" + (System.currentTimeMillis() - start) + "ms");
            }
            result = next;
            if (result.isEmpty()) {
                break;
            }
        }
        return result;
    }
}

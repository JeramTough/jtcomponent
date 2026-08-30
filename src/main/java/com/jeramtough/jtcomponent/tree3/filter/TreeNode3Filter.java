package com.jeramtough.jtcomponent.tree3.filter;

import com.jeramtough.jtcomponent.tree3.core.TreeNode3;

/**
 * <pre>
 *     节点过滤器：决定某个节点是否保留。
 *     多个过滤器之间为 AND 语义（全部 accept 才保留）。
 *
 * Created on 2024/11/26 下午8:14
 * by @author WeiBoWen
 * </pre>
 */
public interface TreeNode3Filter {

    /**
     * @return 过滤器执行顺序，数值越小越先执行
     */
    int getOrderNumber();

    /**
     * 判断节点是否保留。
     *
     * @param treeNode 待判断的节点
     * @return true 保留，false 排除
     */
    <T> boolean accept(TreeNode3<T> treeNode);
}

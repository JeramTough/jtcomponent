package com.jeramtough.jtcomponent.tree2.builder;

import com.jeramtough.jtcomponent.tree2.core.Tree2;

/**
 * <pre>
 * Created on 2025/7/17 下午4:36
 * by @author WeiBoWen
 * </pre>
 */
public interface Tree2Builder<T> {

    /**
     * 没有父节点找到策略，直接抛弃
     */
    public static Integer NO_PARENT_STRATEGY_NODE = 0;

    /**
     * 没有父节点找到策略，放到根节点下
     */
    public static Integer NO_PARENT_STRATEGY_INTO_ROOT_NODE = 1;

    Tree2<T> build();
}

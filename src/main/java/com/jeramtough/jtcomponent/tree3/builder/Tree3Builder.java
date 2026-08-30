package com.jeramtough.jtcomponent.tree3.builder;

import com.jeramtough.jtcomponent.tree3.core.Tree3;

/**
 * <pre>
 *     树构建器：由适配器或数据源构建出一棵完整的 Tree3。
 *
 * Created on 2025/7/17 下午4:36
 * by @author WeiBoWen
 * </pre>
 */
public interface Tree3Builder<T> {

    /**
     * 找不到父节点时：直接抛弃该节点
     */
    int NO_PARENT_STRATEGY_NODE = 0;

    /**
     * 找不到父节点时：放入根节点下
     */
    int NO_PARENT_STRATEGY_INTO_ROOT_NODE = 1;

    /**
     * 以默认参数构建 Tree3（不打印耗时日志）。
     *
     * @return 构建好的 Tree3 对象
     */
    Tree3<T> build();

    /**
     * 构建 Tree3。
     *
     * @param isShowDetailLog 是否打印构建过程中的耗时日志
     * @return 构建好的 Tree3 对象
     */
    Tree3<T> build(boolean isShowDetailLog);
}

package com.jeramtough.jtcomponent.tree3.rebuilder;

import com.jeramtough.jtcomponent.tree3.core.Tree3;

/**
 * <pre>
 *     树重构器：基于已有树，重新构建一棵新树（过滤、取子树、截断等）。
 *
 * Created on 2025/7/17 下午10:33
 * by @author WeiBoWen
 * </pre>
 */
public interface Tree3Rebuilder<T> {

    /**
     * 重构并返回一棵新树，不修改原树。
     *
     * @return 重构后的新树
     */
    Tree3<T> rebuild();
}

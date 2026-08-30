package com.jeramtough.jtcomponent.tree3.adapter;

import com.jeramtough.jtcomponent.tree3.core.TreeNode3;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     懒加载器：选中某个节点时，才真正加载它的「下一级」子节点。
 *
 *     当节点未设置该加载器时，视为全量加载模式（子节点已在内存中）。
 *     当节点设置了该加载器且尚未加载时，调用 {@link TreeNode3#getSubs()} 会触发加载并缓存。
 *
 * Created on 2025/8/30
 * by @author WeiBoWen
 * </pre>
 */
@FunctionalInterface
public interface ChildrenLoader3<T> extends Serializable {

    /**
     * @param parent 当前节点
     * @return 下一级子节点的适配器集合（可能为空，不能为 null）
     */
    List<OneTreeNode3Adapter<T>> loadChildren(TreeNode3<T> parent);
}

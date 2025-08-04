package com.jeramtough.jtcomponent.tree.adapter;

/**
 * 这是为了组装tree
 *
 * @see com.jeramtough.jtcomponent.tree.processor.TreeProcessor
 * <p>
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 */
public interface OneTreeNodeAdapter<T> {

    Object getKey();

    /**
     * 最上层就返回null，返回null挂在rootTreeNode下，如果key不为null
     * ，但是找不到parentKey对应的treenode，则抛弃这个节点
     *
     * @return 父节点的key
     */
    Object getParentKey();

    T getValue();

    int getOrder();

    default String getExpression() {
        String key = this.getKey() == null ? "null" : this.getKey().toString();
        return key;
    }
}

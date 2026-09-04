package com.jeramtough.jtcomponent.tree3.adapter;

/**
 * <pre>
 *     扁平数据源适配器：把一个「数据库表里的每一条记录」适配成树节点。
 *     适用于 EveryoneTree3Builder（每条数据自带 parentKey 的场景）。
 *
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 * </pre>
 */
public interface OneTreeNode3Adapter<T> {

    /**
     * 设置原始数据源（数据库记录、JSON 对象等）。
     *
     * @param source 原始数据源
     */
    void setSource(Object source);

    /**
     * @return 节点承载的业务对象
     */
    T getValue();

    /**
     * @return 节点唯一标识
     */
    String getKey();

    /**
     * @return 父节点的 key，返回 null 或空字符串代表是根节点
     */
    String getParentKey();

    /**
     * @return 节点的业务编码，可为 null
     */
    default String getCode() {
        return null;
    }

    /**
     * 得到节点的名称
     */
    default String getName() {
        return getKey();
    }

    /**
     * @return 同层级内的排序权重，数值越小越靠前，默认 0
     */
    default int getOrder() {
        return 0;
    }
}

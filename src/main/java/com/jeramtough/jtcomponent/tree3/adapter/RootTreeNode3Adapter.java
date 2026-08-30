package com.jeramtough.jtcomponent.tree3.adapter;

import java.util.List;

/**
 * <pre>
 *     递归根节点适配器：适配一个可以递归往下找子节点的根对象。
 *     适用于 RootTree3Builder。
 *
 *     File rootFile = new File("/path/to/root");
 *     RootTreeNode3Adapter adapter = new FileRootTreeNode3Adapter(rootFile);
 *
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 * </pre>
 */
public interface RootTreeNode3Adapter<T> {

    /**
     * @return 当前节点承载的业务对象
     */
    T getValue();

    /**
     * @return 当前节点唯一标识
     */
    String getKey();

    /**
     * @return 父对象，返回 null 时表示该节点是根节点
     */
    T getParent();

    /**
     * @return 直接子对象列表（业务层对象，非 TreeNode3）
     */
    List<T> getSubs();

    /**
     * @return 是否有直接子对象
     */
    boolean hasSubs();

    /**
     * 为子对象创建一个新的适配器实例（工厂方法）。
     *
     * @param value 子对象
     * @return 子对象对应的适配器
     */
    RootTreeNode3Adapter<T> getNewInstance(T value);
}

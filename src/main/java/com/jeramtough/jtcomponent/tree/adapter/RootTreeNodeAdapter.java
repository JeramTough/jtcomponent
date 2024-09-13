package com.jeramtough.jtcomponent.tree.adapter;

import java.util.List;

/**
 *这是为了拆tree
 *
 * 遍历一个根节点的适配器
 *
 * File rootFile = new File("C:\\Users\\weibw\\Desktop");
 * FileRootTreeNodeAdapter adapter = new FileRootTreeNodeAdapter(rootFile);
 * TreeProcessor treeProcessor = new DefaultTreeProcessor();
 * TreeNode treeNode = treeProcessor.processing(true, adapter);
 *
 *
 * Created on 2019/7/12 14:46
 * by @author WeiBoWen
 */
public interface RootTreeNodeAdapter<T> {

    T getParent();

    List<T> getSubs();

    boolean hasSubs();

    RootTreeNodeAdapter<T> getNewInstance(T t);

    T get();

}

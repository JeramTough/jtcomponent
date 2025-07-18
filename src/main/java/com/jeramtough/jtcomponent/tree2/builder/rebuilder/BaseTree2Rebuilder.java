package com.jeramtough.jtcomponent.tree2.builder.rebuilder;

import com.jeramtough.jtcomponent.tree2.adpater.OneTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.adpater.RebuildOneTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.adpater.RootTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.builder.EveryoneTree2Builder;
import com.jeramtough.jtcomponent.tree2.builder.RootTree2Builder;
import com.jeramtough.jtcomponent.tree2.builder.Tree2Builder;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2025/7/17 下午10:35
 * by @author WeiBoWen
 * </pre>
 */
public abstract class BaseTree2Rebuilder<T> implements Tree2Rebuilder<T> {

    private final Tree2<T> tree2;

    protected BaseTree2Rebuilder(Tree2<T> tree2) {
        this.tree2 = tree2;
    }

    /**
     * @param treeNode2List 每一个节点的数据
     * @return Tree2
     */
    public Tree2<T> rebuildByEveryOneTreeNodeList(List<TreeNode2<T>> treeNode2List,
                                                  int noParentStrategy,
                                                  TreeNode2SortMethod treeNode2SortMethod) {
        //计算耗时
        long startTime = System.currentTimeMillis();
        System.out.println(
                "开始使用everyOneTreeNodeList重构Tree2...,数据源共" + treeNode2List.size() + "个");

        List<OneTreeNode2Adapter<T>> adapterList =
                treeNode2List
                        .parallelStream()
                        .map(tTreeNode2 -> {
                            RebuildOneTreeNode2Adapter<T> adapter =
                                    new RebuildOneTreeNode2Adapter<T>();
                            adapter.setSource(tTreeNode2);
                            return adapter;
                        })
                        .collect(Collectors.toList());

        //开始重构
        Tree2<T> tree2 = new EveryoneTree2Builder<T>()
                .setNoParentStrategy(noParentStrategy)
                .setAdapterList(adapterList)
                .setTreeNode2SortMethod(treeNode2SortMethod)
                .build();

        long hTime = (System.currentTimeMillis() - startTime);
        System.out.println("使用everyOneTreeNodeList重构Tree2结束，耗时：" + hTime + "毫秒");

        return tree2;

    }

    /*public Tree2 rebuildByRootTreeNodeList(
            Class<? extends RootTreeNode2Adapter<T>> rootTreeNodeAdapterClass,
            Tree2 tree2, List<?> souceList) {

        //计算耗时
        long startTime = System.currentTimeMillis();
        System.out.println(
                "开始使用rootTreeNodeList重构Tree2...,数据源共" + souceList.size() + "个");
        //开始重构

        RootTree2Builder<Object> rootTree2Builder = new RootTree2Builder<>()
                .setRoot(true)
                .setTreeNode2SortMethod(getSortMethod())
                .setRootSourceList(souceList)
                .setRootTreeNodeAdapterClass(rootTreeNodeAdapterClass);

        Tree2 rebuildTree2 = rootTree2Builder.build();
        long hTime = (System.currentTimeMillis() - startTime);
        System.out.println("重构Tree2结束，耗时：" + hTime + "毫秒");

        return rebuildTree2;
    }*/


    public Tree2<T> getTree2() {
        return tree2;
    }


}

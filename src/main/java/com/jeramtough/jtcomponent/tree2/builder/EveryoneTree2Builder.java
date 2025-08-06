package com.jeramtough.jtcomponent.tree2.builder;

import com.jeramtough.jtcomponent.tree2.adpater.OneTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.core.DefaultTree2;
import com.jeramtough.jtcomponent.tree2.core.DefaultTreeNode2;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2Comparator;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *     创建一个树，使用每一个子节点的源数据，类似数据库表里的每一个数据
 *
 *
 * Created on 2025/7/17 下午4:29
 * by @author WeiBoWen
 * </pre>
 */
public class EveryoneTree2Builder<T> extends BaseTree2Builder<T> implements Tree2Builder<T> {

    private Integer noParentStrategy;
    private List<OneTreeNode2Adapter<T>> adapterList = null;
    private TreeNode2SortMethod treeNode2SortMethod;

    public EveryoneTree2Builder<T> setNoParentStrategy(Integer noParentStrategy) {
        this.noParentStrategy = noParentStrategy;
        return this;
    }


    public EveryoneTree2Builder<T> setAdapterList(List<OneTreeNode2Adapter<T>> adapterList) {
        this.adapterList = adapterList;
        return this;
    }


    public EveryoneTree2Builder<T> setTreeNode2SortMethod(
            TreeNode2SortMethod treeNode2SortMethod) {
        this.treeNode2SortMethod = treeNode2SortMethod;
        return this;
    }

    @Override
    public Tree2<T> build() {

        if (noParentStrategy == null) {
            throw new IllegalArgumentException("noParentStrategy 不能为null");
        }

        if (adapterList == null) {
            throw new NullPointerException("adapterList is null");
        }

        if (treeNode2SortMethod == null) {
            treeNode2SortMethod = TreeNode2SortMethod.ASCENDING;
        }

        return processing(adapterList, noParentStrategy);
    }


//******************************************

    private Tree2<T> processing(List<? extends OneTreeNode2Adapter<T>> oneTreeNodeAdapterList,
                                int noParentStrategy) {

        //计算耗时
        long startTime = System.currentTimeMillis();
        System.out.println(
                "开始使用oneTreeNodeAdapterList构建Tree2...,数据源共" + adapterList.size() + "个");

        DefaultTree2<T> tree2 = new DefaultTree2<T>();

        //所有节点创建映射的Map集合，存入Tree2对象
        oneTreeNodeAdapterList.parallelStream().map(adapter -> {
            DefaultTreeNode2<T> treeNode = new DefaultTreeNode2<T>(adapter.getKey(),
                    adapter.getValue());
            treeNode.setOrder(adapter.getOrder());
            treeNode.setParentKey(adapter.getParentKey());
            treeNode.setCode(adapter.getCode());
            return treeNode;
        }).forEach(tree2::put);


        List<TreeNode2<T>> rootTreeNodeList = new ArrayList<>();

        for (OneTreeNode2Adapter<T> adapter : oneTreeNodeAdapterList) {
            TreeNode2<T> thisTreeNode = tree2.getTreeNodeByIdKey(adapter.getKey());
            String parentKey = adapter.getParentKey();
            if (parentKey == null) {
                rootTreeNodeList.add(thisTreeNode);
            }
            else {
                TreeNode2<T> parentTreeNode = tree2.getTreeNodeByIdKey(adapter.getParentKey());
                if (parentTreeNode == null) {
                    if (noParentStrategy == 1) {
                        rootTreeNodeList.add(thisTreeNode);
                        System.out.println(
                                "warn:该" + adapter.getKey() + "节点找不到对应的父节点" + adapter.getParentKey() + "," + "所以添加到根节点rootTreeNode.");
                    }
                    else {
                        System.out.println(
                                "warn:该" + adapter.getKey() + "节点找不到对应的父节点" + adapter.getParentKey() + "," + "所以抛弃该数据.");
                    }

                }
                else {
                    parentTreeNode.addSubs(treeNode2SortMethod, thisTreeNode);
                }
            }

        }

        //排序
        TreeNode2Comparator comparator = new TreeNode2Comparator(treeNode2SortMethod);
        rootTreeNodeList.sort(comparator);

        tree2.setRootTreeNodeList(rootTreeNodeList);

        long hTime = (System.currentTimeMillis() - startTime);
        System.out.println(
                "使用oneTreeNodeAdapterList构建Tree2结束,树共" + tree2.getAllIdKeyTreeNodeMap().size() +
                        "个节点，耗时：" + hTime + "毫秒");

        return tree2;
    }


}

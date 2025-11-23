package com.jeramtough.jtcomponent.tree2.builder.rebuilder;

import com.jeramtough.jtcomponent.tree2.builder.Tree2Builder;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * Created on 2025/7/17 下午10:34
 * by @author WeiBoWen
 * </pre>
 */
public class MaxRetainSubTree2Rebuilder<T> extends BaseTree2Rebuilder<T>
        implements Tree2Rebuilder<T> {

    //最大保留子节点的层级树
    private Integer maxRetainSubNodeLevel = null;


    public MaxRetainSubTree2Rebuilder(Tree2<T> tree2) {
        super(tree2);
    }


    public MaxRetainSubTree2Rebuilder<T> setMaxRetainSubNodeLevel(
            Integer maxRetainSubNodeLevel) {
        this.maxRetainSubNodeLevel = maxRetainSubNodeLevel;
        return this;
    }

    @Override
    public Tree2<T> rebuild() {


        if (maxRetainSubNodeLevel == null) {
            throw new RuntimeException("maxRetainSubNodeLevel不能为null");
        }

        //计算耗时
        long startTime = System.currentTimeMillis();
        System.out.println(
                "开始使用subTreeNodeKey重构Tree2...,旧树共" + getTree2().getAllIdKeyTreeNodeMap().size() + "个节点");

        Map<String, TreeNode2<T>> filterTreeNode2Map = new ConcurrentHashMap<>();


        List<TreeNode2<T>> tempTreeNode2List = new ArrayList<>(
                getTree2().getRootTreeNodeList());
        for (int i = 0; i < maxRetainSubNodeLevel; i++) {
            List<TreeNode2<T>> myTreeNode2List2 = new ArrayList<>();
            for (TreeNode2<T> tTreeNode2 : tempTreeNode2List) {
                myTreeNode2List2.add(tTreeNode2);

                filterTreeNode2Map.put(tTreeNode2.getKey(), tTreeNode2);
            }
            tempTreeNode2List = myTreeNode2List2;
            if (tempTreeNode2List.isEmpty()) {
                break;
            }
        }

        //清除子节点,,敏感操作，需要克隆后再进行节点删改
        for (TreeNode2<T> tTreeNode2 : tempTreeNode2List) {
            String key = tTreeNode2.getKey();
            TreeNode2<T> tTreeNode22 = filterTreeNode2Map.get(key);
            Objects.requireNonNull(tTreeNode22);
            TreeNode2<T> tTreeNode222= tTreeNode2.clone();
            tTreeNode222.getSubs().clear();
            filterTreeNode2Map.put(key, tTreeNode222);
        }

        List<TreeNode2<T>> filterTreeNode2List = new ArrayList<>(filterTreeNode2Map.values());

        Tree2<T> newTree2 = super.rebuildByEveryOneTreeNodeList(filterTreeNode2List,
                Tree2Builder.NO_PARENT_STRATEGY_NODE,
                getTree2().getSortMethod());

        long hTime = (System.currentTimeMillis() - startTime);
        System.out.println(
                "使用subTreeNodeKey重构Tree2结束,新树共" + newTree2.getAllIdKeyTreeNodeMap().

                                                                   size() + "个节点，耗时：" + hTime + "毫秒");


        return newTree2;

    }

    //***************************


}

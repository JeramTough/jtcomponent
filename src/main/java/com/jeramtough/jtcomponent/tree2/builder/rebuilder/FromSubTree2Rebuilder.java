package com.jeramtough.jtcomponent.tree2.builder.rebuilder;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.jtcomponent.tree2.builder.Tree2Builder;
import com.jeramtough.jtcomponent.tree2.core.DefaultTree2;
import com.jeramtough.jtcomponent.tree2.core.DefaultTreeNode2;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.tree2.util.TreeNode2Utils;
import com.jeramtough.jtcomponent.utils.JtCollectionUtil;
import com.jeramtough.jtcomponent.utils.JtStrUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2025/7/17 下午10:34
 * by @author WeiBoWen
 * </pre>
 */
public class FromSubTree2Rebuilder<T> extends BaseTree2Rebuilder<T>
        implements Tree2Rebuilder<T> {

    private String subTreeNodeKey;


    public FromSubTree2Rebuilder(Tree2<T> tree2) {
        super(tree2);
    }


    public FromSubTree2Rebuilder<T> setSubTreeNodeKey(String subTreeNodeKey) {
        this.subTreeNodeKey = subTreeNodeKey;
        return this;
    }

    @Override
    public Tree2<T> rebuild() {

        //计算耗时
        long startTime = System.currentTimeMillis();
        System.out.println(
                "开始使用subTreeNodeKey重构Tree2...,旧树共" + getTree2().getAllIdKeyTreeNodeMap().size() + "个节点");

        //先找到需要的子节点的层级的节点
        List<TreeNode2<T>> selectedTreeNode2List = new ArrayList<>();
        if (JtStrUtil.isEmpty(subTreeNodeKey)) {
            List<TreeNode2<T>> treeNode2List2 = getTree2().getRootTreeNodeList();
            selectedTreeNode2List.addAll(treeNode2List2);
        }
        else {
            final TreeNode2<T> subTreeNode = getTree2().getTreeNodeByIdKey(subTreeNodeKey);
            if (subTreeNode == null) {
                throw new RuntimeException(
                        "请设置正确的子树节点的key,该key没有找到对应的子节点");
            }
            List<TreeNode2<T>> treeNode2List2 = subTreeNode.getSubs();
            selectedTreeNode2List.addAll(treeNode2List2);
        }

        //从此，这些子节点没有了父节点,敏感操作，需要克隆后再进行节点删改
        selectedTreeNode2List = selectedTreeNode2List
                .parallelStream()
                .map(TreeNode2::clone)
                .collect(Collectors.toList());
        selectedTreeNode2List.parallelStream().forEach(tTreeNode2 -> {
            tTreeNode2.setParentKey(null);
        });

        //然后收集所有子节点
        List<TreeNode2<T>> allTreeNode2List =
                selectedTreeNode2List
                        .parallelStream()
                        .map(TreeNode2::getAllSubs)
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

        //包括自己在内
        allTreeNode2List.addAll(selectedTreeNode2List);

        Tree2<T> newTree2 = super.rebuildByEveryOneTreeNodeList(allTreeNode2List,
                Tree2Builder.NO_PARENT_STRATEGY_NODE,
                getTree2().getSortMethod());


        long hTime = (System.currentTimeMillis() - startTime);
        System.out.println(
                "使用subTreeNodeKey重构Tree2结束,新树共" + newTree2.getAllIdKeyTreeNodeMap().

                                                                   size() + "个节点，耗时：" + hTime + "毫秒");

        return newTree2;

//       return super.rebuildByRootTreeNodeList(rootTreeNodeAdapterClass, getTree2(), valueList);
    }

    //***************************


}

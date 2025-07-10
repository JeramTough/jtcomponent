package com.jeramtough.jtcomponent.tree.processor;

import com.jeramtough.jtcomponent.tree.adapter.OneTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.adapter.RootTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;

import java.util.*;

/**
 * Created on 2019/7/12 9:04
 * by @author WeiBoWen
 */
public class DefaultTreeProcessor implements TreeProcessor {


    @Override
    public TreeNode processing(boolean root, RootTreeNodeAdapter adapter) {

        LinkedList<RootTreeNodeAdapter> tempTreeAdapterLinkedList = new LinkedList<>();
        TreeNode rootTreeNode;
        RootTreeNodeAdapter rootAdapter;
        Map<RootTreeNodeAdapter, TreeNode> keyAdapterTreeStructureMap = new HashMap<>();
//        TreeNode startTreeNode = new DefaultTreeNode(adapter.get());


        if (root) {
            rootAdapter = adapter;
        }
        else {
            for (; ; ) {
                Object parentObject = adapter.getParent();
                if (parentObject != null) {
                    adapter = adapter.getNewInstance(parentObject);
                }
                else {
                    rootAdapter = adapter;
                    break;
                }
            }
        }

        rootTreeNode = new DefaultTreeNode(rootAdapter.get());


        if (rootAdapter.hasSubs()) {
            List subList = rootAdapter.getSubs();
            for (Object o : subList) {
                RootTreeNodeAdapter newAdapter = adapter.getNewInstance(o);
                tempTreeAdapterLinkedList.add(newAdapter);

                TreeNode treeNode = new DefaultTreeNode(newAdapter.get());
                rootTreeNode.addSub(treeNode);
                keyAdapterTreeStructureMap.put(newAdapter, treeNode);
            }

            RootTreeNodeAdapter tempAdapter;
            while (!tempTreeAdapterLinkedList.isEmpty()) {
                tempAdapter = tempTreeAdapterLinkedList.removeFirst();
                TreeNode treeNode = keyAdapterTreeStructureMap.get(tempAdapter);

                if (tempAdapter.hasSubs()) {
                    List subList1 = tempAdapter.getSubs();

                    for (Object o1 : subList1) {
                        RootTreeNodeAdapter newAdapter = adapter.getNewInstance(o1);
                        if (newAdapter.hasSubs()) {
                            tempTreeAdapterLinkedList.add(newAdapter);
                        }

                        TreeNode treeNode1 = new DefaultTreeNode(newAdapter.get());
                        keyAdapterTreeStructureMap.put(newAdapter, treeNode1);
                        treeNode.addSub(treeNode1);
                    }
                }
            }

        }

        return rootTreeNode;
    }

    @Override
    public <T> TreeNode processing(
            List<? extends OneTreeNodeAdapter<T>> oneTreeNodeAdapterList) {
        TreeNode rootTreeNode = new DefaultTreeNode();

        Map<Object, TreeNode> idKeyTreeNodeMap = new HashMap<>();

        for (OneTreeNodeAdapter<T> adapter : oneTreeNodeAdapterList) {
            TreeNode treeNode = new DefaultTreeNode(adapter.getValue(),
                    adapter.getKey().toString());
            treeNode.setOrder(adapter.getOrder());
            treeNode.setExpression(adapter.getKey().toString());
            idKeyTreeNodeMap.put(adapter.getKey(), treeNode);
        }

        for (OneTreeNodeAdapter<T> adapter : oneTreeNodeAdapterList) {
            TreeNode thisTreeNode = idKeyTreeNodeMap.get(adapter.getKey());
            Object parentKey = adapter.getParentKey();
            if (parentKey == null) {
                rootTreeNode.addSub(thisTreeNode);
            }
            else {
                TreeNode parentTreeNode = idKeyTreeNodeMap.get(adapter.getParentKey());
                if (parentTreeNode == null) {
                    System.out.println("warn:该节点找不到对应的父节点,所以添加到根节点rootTreeNode，parentKey:" + adapter.getParentKey());
                    rootTreeNode.addSub(thisTreeNode);
                }
                else {
                    parentTreeNode.addSub(thisTreeNode);
                }
            }

        }

        //更新路径值
        TreeNodeUtils.updatePathsAndLevel(rootTreeNode);

        return rootTreeNode;
    }
}

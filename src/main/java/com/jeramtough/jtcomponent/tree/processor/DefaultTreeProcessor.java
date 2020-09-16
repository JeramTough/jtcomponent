package com.jeramtough.jtcomponent.tree.processor;

import com.jeramtough.jtcomponent.tree.adapter.TreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2019/7/12 9:04
 * by @author WeiBoWen
 */
public class DefaultTreeProcessor implements TreeProcessor {


    @Override
    public TreeNode processing(boolean root, TreeNodeAdapter adapter) {

        LinkedList<TreeNodeAdapter> tempTreeAdapterLinkedList = new LinkedList<>();
        TreeNode rootTreeNode;
        TreeNodeAdapter rootAdapter;
        Map<TreeNodeAdapter, TreeNode> keyAdapterTreeStructureMap = new HashMap<>();
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
                TreeNodeAdapter newAdapter = adapter.getNewInstance(o);
                tempTreeAdapterLinkedList.add(newAdapter);

                TreeNode treeNode = new DefaultTreeNode(newAdapter.get());
                rootTreeNode.addSub(treeNode);
                keyAdapterTreeStructureMap.put(newAdapter, treeNode);
            }

            TreeNodeAdapter tempAdapter;
            while (!tempTreeAdapterLinkedList.isEmpty()) {
                tempAdapter = tempTreeAdapterLinkedList.removeFirst();
                TreeNode treeNode = keyAdapterTreeStructureMap.get(tempAdapter);

                if (tempAdapter.hasSubs()) {
                    List subList1 = tempAdapter.getSubs();

                    for (Object o1 : subList1) {
                        TreeNodeAdapter newAdapter = adapter.getNewInstance(o1);
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
}

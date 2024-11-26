package com.jeramtough.jtcomponent.tree.structure;

import com.jeramtough.jtcomponent.tree.base.SortMethod;
import com.jeramtough.jtcomponent.tree.comparator.TreeNodeComparator;
import com.jeramtough.jtcomponent.tree.expression.TreeContext;
import com.jeramtough.jtcomponent.tree.expression.core.TreeExpression;
import com.jeramtough.jtcomponent.tree.expression.interpret.DefaultExpressionInterpreter;
import com.jeramtough.jtcomponent.tree.expression.interpret.ExpressionInterpreter;
import com.jeramtough.jtcomponent.tree.foreach.NodeCaller;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created on 2019/7/11 15:44
 * by @author WeiBoWen
 */
public class DefaultTreeNode implements TreeNodeAble {

    private String key;
    private Object value;
    private List<TreeNode> subTreeNodes;
    private TreeNode parentTreeNode;
    private int level = 0;
    private Predicate<TreeNode> subFilters;
    private int order = 0;
    private String expression;

    private List<String> paths;

    public DefaultTreeNode() {
        subTreeNodes = new ArrayList<>();
        paths = new ArrayList<>();
    }

    public DefaultTreeNode(Object value) {
        this();
        this.value = value;
    }


    public DefaultTreeNode(Object value, String key) {
        this();
        this.value = value;
        this.key = key;
    }

    public DefaultTreeNode(Object value, String key, String expression) {
        this();
        this.value = value;
        this.key = key;
        this.expression = expression;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean isRoot() {
        return parentTreeNode == null;
    }

    @Override
    public boolean hasSubs() {
        return getSubs().size() > 0;
    }

    @Override
    public List<TreeNode> getSubs() {
        return subTreeNodes;
    }

    @Override
    public List<TreeNode> getAllSubs() {
        List<TreeNode> allTreeNodes = TreeNodeUtils.getAll(this, SortMethod.DESCENDING, false);
        return allTreeNodes;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public List<String> getPaths() {
        return paths;
    }

    @Override
    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public TreeNode getParent() {
        return parentTreeNode;
    }

    @Override
    public void setParent(TreeNode parentTreeNode) {
        this.parentTreeNode = parentTreeNode;
    }

    @Override
    public TreeNode addSub(TreeNode treeNode) {
        //先不排序添添加，最后在排序
        addSubButDontSort(treeNode);
        //排序
        TreeNodeComparator comparator = new TreeNodeComparator();
        subTreeNodes.sort(comparator);

        return this;
    }

    @Override
    public TreeNode addSubs(TreeNode... treeNodes) {
        //先不排序添添加，最后在排序
        for (TreeNode treeNode : treeNodes) {
            addSubButDontSort(treeNode);
        }
        //排序
        TreeNodeComparator comparator = new TreeNodeComparator();
        subTreeNodes.sort(comparator);
        return this;
    }

    @Override
    public TreeNode andFilter(Predicate<TreeNode> filter) {
        if (subFilters == null) {
            subFilters = filter;
        }
        else {
            subFilters = subFilters.and(filter);
        }
        return this;
    }


    @Override
    public List<TreeNode> getSubsByFilters() {
        if (subFilters == null) {
            return getSubs();
        }
        else {
            List<TreeNode> allSubTreeNodes = getAllSubs();
            List<TreeNode> treeNodeList =
                    allSubTreeNodes.stream().filter(subFilters).collect(Collectors.toList());

            return treeNodeList;
        }
    }

    @Override
    public void doFilters() {
        if (subFilters != null) {
            List<TreeNode> allTreeNode = TreeNodeUtils.getAll(this, SortMethod.DESCENDING);

            allTreeNode
                    .parallelStream()
                    .filter(new Predicate<TreeNode>() {
                        @Override
                        public boolean test(TreeNode treeNode) {
                            //如果该节点是根节点本身，直接过滤掉
                            if (treeNode.equals(DefaultTreeNode.this)) {
                                return false;
                            }
                            //因为是要过滤出要被移除的节点，所有这里取反
                            return !subFilters.test(treeNode);
                        }
                    })
                    .forEach(TreeNode::beMoved);

        }
        else {
            throw new IllegalStateException("No have any filter was add");
        }
    }

    @Override
    public void beMoved() {
        if (!isRoot()) {
            parentTreeNode.getSubs().remove(this);
        }
        else {
            throw new IllegalStateException("The root node can't be removed");
        }
    }

    @Override
    public void moveSubs() {
        this.subTreeNodes = new ArrayList<TreeNode>();
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public List<TreeNode> getAll() {
        return TreeNodeUtils.getAll(this);
    }

    @Override
    public List<TreeNode> getAll(SortMethod sortMethod) {
        return TreeNodeUtils.getAll(this, sortMethod);
    }

    @Override
    public List<List<TreeNode>> getAllForLevel() {
        return getAllForLevel(SortMethod.ASCENDING);
    }

    @Override
    public List<List<TreeNode>> getAllForLevel(SortMethod sortMethod) {
        return TreeNodeUtils.getAllForLevel(this, sortMethod);
    }

    @Override
    public List<TreeNode> getBrothers() {
        List<TreeNode> brothers = new ArrayList<>();

        if (this.getParent() != null) {
            this.getParent().foreach(new NodeCaller() {
                @Override
                public boolean called(TreeNode treeNode) {

                    if (treeNode.getLevel() > DefaultTreeNode.this.level) {
                        return false;
                    }
                    else {
                        if (treeNode.getLevel() == DefaultTreeNode.this.level &&
                                treeNode != DefaultTreeNode.this) {
                            brothers.add(treeNode);
                        }
                        return true;
                    }

                }
            });
        }
        return brothers;
    }

    @Override
    public void foreach(NodeCaller nodeCaller) {
        TreeNodeUtils.foreach(this, nodeCaller);
    }


    @Override
    public String getExpression() {
        return expression;
    }


    @Override
    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public List<TreeNode> query(String expression) {
        TreeContext context = new TreeContext(this);
        ExpressionInterpreter interpreter = new DefaultExpressionInterpreter();
        TreeExpression treeExpression = interpreter.interpret(expression);

        List<TreeNode> treeNodeList = treeExpression.interpret(context);
        return treeNodeList;
    }

    @Override
    public String toString() {
        return "{" +
                "value=" + value +
                ", level=" + level +
                ", hashcode=" + this.hashCode() +
                '}';
    }

    @Override
    public String getDetail() {
        StringBuilder stringBuilder = new StringBuilder();

        List<List<TreeNode>> all = TreeNodeUtils.getAllForLevel(this, SortMethod.ASCENDING);
        for (List<TreeNode> list1 : all) {
            for (TreeNode treeNode : list1) {
                stringBuilder.append(treeNode.toString()).append(", ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public Object clone() {
        DefaultTreeNode newTreeNode = new DefaultTreeNode(getValue());
        newTreeNode.setExpression(this.getExpression());
        newTreeNode.setParent(this.parentTreeNode);
        newTreeNode.setLevel(this.level);
        newTreeNode.setOrder(this.order);
        if (hasSubs()) {
            cloneSubs(newTreeNode, this.subTreeNodes);
        }
        return newTreeNode;
    }

    //*****************

    /**
     * 添加子节点，但是不排序
     */
    private void addSubButDontSort(TreeNode treeNode) {
        subTreeNodes.add(treeNode);

        if (treeNode instanceof TreeNodeAble) {
            TreeNodeAble treeNodeAble = (TreeNodeAble) treeNode;
            treeNodeAble.setParent(this);
        }

        //自增的level加1，自己的子节点也要跟着加1
        int baseLevel = this.level + 1;

        if (treeNode instanceof TreeNodeAble) {

            TreeNodeAble treeNodeAble = (TreeNodeAble) treeNode;
            treeNodeAble.setLevel(baseLevel);

            for (TreeNode subTreeNode : treeNode.getSubs()) {
                TreeNodeAble subTreeNodeAble = (TreeNodeAble) subTreeNode;
                subTreeNodeAble.setLevel(baseLevel + subTreeNode.getLevel());
            }
        }

    }

    private void cloneSubs(TreeNode newParentTreeNode, List<TreeNode> oldSubTreeNodes) {
        newParentTreeNode.moveSubs();

        for (TreeNode oldSubTreeNode : oldSubTreeNodes) {
            TreeNode newSub = new DefaultTreeNode(oldSubTreeNode.getValue());
            newSub.setExpression(oldSubTreeNode.getExpression());
            newSub.setOrder(oldSubTreeNode.getOrder());
            newParentTreeNode.addSub(newSub);
            if (oldSubTreeNode.hasSubs()) {
                this.cloneSubs(newSub, oldSubTreeNode.getSubs());
            }
        }
    }
}

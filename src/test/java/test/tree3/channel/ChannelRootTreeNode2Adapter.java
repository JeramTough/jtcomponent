package test.tree3.channel;


import com.jeramtough.jtcomponent.tree3.adapter.RootTreeNode3Adapter;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2025/7/18 上午1:44
 * by @author WeiBoWen
 * </pre>
 */
public class ChannelRootTreeNode2Adapter implements RootTreeNode3Adapter<Channel> {

    private final Tree3<Channel> tree2;

    private final Channel channel;

    public ChannelRootTreeNode2Adapter(Tree3<Channel> tree2, Channel channel) {
        this.tree2 = tree2;
        this.channel = channel;
    }

    /*@Override
    public void setSource(Object source) {
        this.channel = (Channel) source;
    }*/

    @Override
    public Channel getValue() {
        return channel;
    }

    @Override
    public String getKey() {
        return channel.getId().toString();
    }

    @Override
    public Channel getParent() {
        TreeNode3<Channel> parentTreeNode = tree2.getTreeNodeByIdKey(
                channel.getParentId().toString());
        return parentTreeNode.getValue();
    }

    @Override
    public List<Channel> getSubs() {
        TreeNode3<Channel> treeNode2 = tree2.getTreeNodeByIdKey(channel.getId().toString());
        Objects.requireNonNull(treeNode2);
        if (treeNode2.hasSubs()) {
            return treeNode2
                    .getAllSubs()
                    .parallelStream()
                    .map(treeNode -> (Channel) treeNode.getValue())
                    .collect(Collectors.toList());
        }
        else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean hasSubs() {
        TreeNode3 treeNode2 = tree2.getTreeNodeByIdKey(channel.getId().toString());
        Objects.requireNonNull(treeNode2);
        return treeNode2.hasSubs();
    }

    @Override
    public RootTreeNode3Adapter<Channel> getNewInstance(Channel value) {
        ChannelRootTreeNode2Adapter adapter = new ChannelRootTreeNode2Adapter(tree2, channel);
        return adapter;
    }
}

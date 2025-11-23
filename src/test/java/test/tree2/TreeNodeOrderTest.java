package test.tree2;

import com.alibaba.fastjson2.JSON;
import com.jeramtough.jtcomponent.tree2.adpater.OneTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.builder.EveryoneTree2Builder;
import com.jeramtough.jtcomponent.tree2.builder.rebuilder.FilterTree2Rebuilder;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;
import com.jeramtough.jtlog.facade.L;
import test.tree2.channel.Channel;
import test.tree2.channel.ChannelOneTreeNodeAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Created on 2025/11/23 下午3:39
 * by @author WeiBoWen
 * </pre>
 */
public class TreeNodeOrderTest {

    public static void main(String[] args) {

        String json = null;
        try {
            json = cn.hutool.core.io.IoUtil.readUtf8(
                    new FileInputStream(
                            "/developer/Codes/IdeaCodes/jtcomponent/DOC/channel2.json"));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Channel> channelList = JSON.parseArray(json, Channel.class);
        List<OneTreeNode2Adapter<Channel>> adapterList = new ArrayList<>();
        for (Channel channel : channelList) {
            ChannelOneTreeNodeAdapter adapter = new ChannelOneTreeNodeAdapter();
            adapter.setSource(channel);
            adapterList.add(adapter);
        }

        EveryoneTree2Builder<Channel> everyoneTree2Builder = new EveryoneTree2Builder<>();
        Tree2<Channel> tree2 = everyoneTree2Builder
                .setAdapterList(adapterList)
                .setTreeNode2SortMethod(
                        TreeNode2SortMethod.ASCENDING)
                .setNoParentStrategy(
                        EveryoneTree2Builder.NO_PARENT_STRATEGY_NODE)
                .build();

        Tree2<Channel> tree3 = new FilterTree2Rebuilder<>(tree2)
                .setFilterList(new ArrayList<>())
                .setNoParentStrategy(1)
                .rebuild();

        for (TreeNode2<Channel> treeNode2 : tree3.getAll()) {
            L.debug(treeNode2.getOrderWithLevel());
        }

    }


}

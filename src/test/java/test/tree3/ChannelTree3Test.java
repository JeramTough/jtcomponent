package test.tree3;

import com.alibaba.fastjson2.JSON;
import com.jeramtough.jtcomponent.tree3.adapter.ChildrenLoader3;
import com.jeramtough.jtcomponent.tree3.adapter.OneTreeNode3Adapter;
import com.jeramtough.jtcomponent.tree3.builder.EveryoneTree3Builder;
import com.jeramtough.jtcomponent.tree3.builder.LazyTree3Builder;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.tree3.rebuilder.MaxRetainSubTree3Rebuilder;
import com.jeramtough.jtcomponent.tree3.sort.TreeNode3SortMethod;
import com.jeramtough.jtcomponent.tree3.util.TreeNode3Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test.common.MyKryoUtil;
import test.tree2.channel.Channel;
import test.tree3.channel.ChannelOneTreeNode3Adapter;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 *     tree3 性能对比测试：全量加载 vs 懒加载
 *
 *     1. 构建耗时对比
 *     2. 节点访问耗时对比（快速打开某个节点）
 *     3. 全量后代加载耗时对比
 *     4. Kryo 序列化/反序列化验证
 *     5. 过滤 + 重构耗时
 *
 * Created on 2026/8/30 23:57
 * by @author WeiBoWen
 * </pre>
 */
public class ChannelTree3Test {

    private static List<Channel> channelList;

    @BeforeAll
    public static void loadData() {
        String json;
        try {
            json = cn.hutool.core.io.IoUtil.readUtf8(
                    new FileInputStream(
                            "/developer/Codes/IdeaCodes/jtcomponent/DOC/channel.json"));
        }
        catch (Exception e) {
            throw new RuntimeException("加载 channel.json 失败", e);
        }
        channelList = JSON.parseArray(json, Channel.class);
        System.out.println("========================================");
        System.out.println("加载 channel.json，共 " + channelList.size() + " 条栏目数据");
        System.out.println("========================================");
    }

    //////////////////////////////////////////
    // 1. 全量加载构建 + 操作
    //////////////////////////////////////////

    @Test
    public void fullLoadBuildAndAccess() {
        System.out.println("\n========== 全量加载模式 ==========");

        // 构建
        long t0 = System.currentTimeMillis();
        Tree3<Channel> tree = buildFullTree();
        long buildTime = System.currentTimeMillis() - t0;
        System.out.println("构建耗时: " + buildTime + "ms，总节点数: " + tree.getAllIdKeyTreeNodeMap().size());

        // 快速打开节点：按 key O(1) 查找
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            for (Channel ch : channelList) {
                tree.getTreeNodeByIdKey(ch.getId().toString());
            }
        }
        long lookupTime = System.currentTimeMillis() - t1;
        System.out.println("按 key 查找全部节点 x100 次: " + lookupTime + "ms");

        // 选中根节点，取下一级子节点（已全在内存中）
        TreeNode3<Channel> firstRoot = tree.getRootTreeNodeList().get(0);
        long t2 = System.currentTimeMillis();
        List<TreeNode3<Channel>> subs = firstRoot.getSubs();
        long subsTime = System.currentTimeMillis() - t2;
        System.out.println("取根节点「" + firstRoot.getValue().getName() + "」下一级子节点: "
                + subs.size() + " 个，耗时: " + subsTime + "ms");

        // 选中根节点，加载全部子节点
        long t3 = System.currentTimeMillis();
        List<TreeNode3<Channel>> allDesc = firstRoot.getAllSubs();
        long allDescTime = System.currentTimeMillis() - t3;
        System.out.println("取根节点「" + firstRoot.getValue().getName() + "」全部后代: "
                + allDesc.size() + " 个，耗时: " + allDescTime + "ms");

        // 按层级分组
        long t4 = System.currentTimeMillis();
        List<List<TreeNode3<Channel>>> byLevel = tree.getAllForLevel();
        long levelTime = System.currentTimeMillis() - t4;
        System.out.println("按层级分组: " + byLevel.size() + " 层，耗时: " + levelTime + "ms");

        // Kryo 序列化
        long t5 = System.currentTimeMillis();
        byte[] bytes = MyKryoUtil.serialize(tree, true, true);
        long serTime = System.currentTimeMillis() - t5;
        System.out.println("Kryo 序列化: " + bytes.length + " bytes，耗时: " + serTime + "ms");

        long t6 = System.currentTimeMillis();
        Tree3<Channel> tree2 = MyKryoUtil.deserializeObject(bytes, Tree3.class, true);
        long deserTime = System.currentTimeMillis() - t6;
        System.out.println("Kryo 反序列化: 耗时 " + deserTime + "ms");
        System.out.println("Kryo 反序列化后节点数: " + tree2.getAllIdKeyTreeNodeMap().size());
    }

    //////////////////////////////////////////
    // 2. 懒加载构建 + 操作
    //////////////////////////////////////////

    @Test
    public void lazyLoadBuildAndAccess() {
        System.out.println("\n========== 懒加载模式 ==========");

        // 按 parentId 分组，parentId == null 或 0L 视为根节点
        Map<String, List<Channel>> byParent =
                TreeNode3Utils.groupByParentId(channelList, Channel::getParentId);
        System.out.println("根节点数: " + byParent.getOrDefault("", new ArrayList<>()).size());

        // 加载器：模拟数据库按 parentId 查询
        // parent.getKey() 是节点 key（非 null），与 byParent 的 key 对应
        ChildrenLoader3<Channel> loader = parent -> {
            List<Channel> children = byParent.getOrDefault(parent.getKey(), new ArrayList<>());
            return children.stream().map(this::toAdapter).collect(Collectors.toList());
        };

        // 构建（只构建根节点）
        long t0 = System.currentTimeMillis();
        List<Channel> rootChannels = byParent.getOrDefault("", new ArrayList<>());
        List<OneTreeNode3Adapter<Channel>> rootAdapters = rootChannels.stream()
                .map(this::toAdapter)
                .collect(Collectors.toList());

        Tree3<Channel> tree = new LazyTree3Builder<Channel>()
                .setRootAdapterList(rootAdapters)
                .setChildrenLoader(loader)
                .build();
        long buildTime = System.currentTimeMillis() - t0;
        System.out.println("构建耗时（仅根节点）: " + buildTime + "ms，根节点数: "
                + tree.getRootTreeNodeList().size());

        // 选中根节点，点击打开下一级（懒加载触发）
        TreeNode3<Channel> firstRoot = tree.getRootTreeNodeList().get(0);
        long t1 = System.currentTimeMillis();
        List<TreeNode3<Channel>> subs = firstRoot.getSubs();
        long subsTime = System.currentTimeMillis() - t1;
        System.out.println("首次点击根节点「" + firstRoot.getValue().getName() + "」加载下一级: "
                + subs.size() + " 个，耗时: " + subsTime + "ms");

        // 再次点击同一节点（已加载，不触发加载器）
        long t2 = System.currentTimeMillis();
        List<TreeNode3<Channel>> subsAgain = firstRoot.getSubs();
        long subsAgainTime = System.currentTimeMillis() - t2;
        System.out.println("再次点击同一节点（缓存命中）: "
                + subsAgain.size() + " 个，耗时: " + subsAgainTime + "ms");

        // 继续点击下一级
        if (!subs.isEmpty()) {
            TreeNode3<Channel> level1Node = subs.get(0);
            long t3 = System.currentTimeMillis();
            List<TreeNode3<Channel>> l1Subs = level1Node.getSubs();
            long l1Time = System.currentTimeMillis() - t3;
            System.out.println("点击二级节点「" + level1Node.getValue().getName() + "」加载下一级: "
                    + (l1Subs == null ? 0 : l1Subs.size()) + " 个，耗时: " + l1Time + "ms");
        }

        // 全量加载后代：选中根节点，递归加载所有子节点
        firstRoot = tree.getRootTreeNodeList().get(0);
        long t4 = System.currentTimeMillis();
        List<TreeNode3<Channel>> allDesc = firstRoot.getAllSubs();
        long allDescTime = System.currentTimeMillis() - t4;
        System.out.println("全量加载根节点「" + firstRoot.getValue().getName() + "」全部后代: "
                + allDesc.size() + " 个，耗时: " + allDescTime + "ms");

        // 总节点数（加载全部后）
        long t5 = System.currentTimeMillis();
        int totalNodes = 0;
        for (TreeNode3<Channel> root : tree.getRootTreeNodeList()) {
            totalNodes += 1 + root.getAllSubs().size();
        }
        long totalTime = System.currentTimeMillis() - t5;
        System.out.println("全量加载所有根节点后代，总节点数: " + totalNodes + "，耗时: " + totalTime + "ms");
    }

    //////////////////////////////////////////
    // 3. 节点重构性能
    //////////////////////////////////////////

    @Test
    public void rebuildPerformance() {
        System.out.println("\n========== 重构性能 ==========");
        Tree3<Channel> tree = buildFullTree();

        // MaxRetainSubTree3Rebuilder：按层级截断
        long t0 = System.currentTimeMillis();
        Tree3<Channel> truncated = new MaxRetainSubTree3Rebuilder<>(tree)
                .setMaxRetainSubNodeLevel(2)
                .rebuild();
        long truncateTime = System.currentTimeMillis() - t0;
        System.out.println("按层级截断（保留到 level 2）: " + truncated.getAllIdKeyTreeNodeMap().size()
                + " 个节点，耗时: " + truncateTime + "ms");

        // 按 key 打开截断后的树
        long t1 = System.currentTimeMillis();
        for (Channel ch : channelList) {
            truncated.getTreeNodeByIdKey(ch.getId().toString());
        }
        long lookupTime = System.currentTimeMillis() - t1;
        System.out.println("截断后按 key 查找全部节点: " + lookupTime + "ms");
    }

    //////////////////////////////////////////
    // 4. 全量加载 + Kryo 序列化验证
    //////////////////////////////////////////

    @Test
    public void fullLoadWithKryo() {
        System.out.println("\n========== Kryo 序列化验证 ==========");
        Tree3<Channel> tree = buildFullTree();
        int originalSize = tree.getAllIdKeyTreeNodeMap().size();
        System.out.println("原始节点数: " + originalSize);

        // 序列化
        long t0 = System.currentTimeMillis();
        byte[] bytes = MyKryoUtil.serialize(tree, true, true);
        long serTime = System.currentTimeMillis() - t0;
        System.out.println("序列化: " + bytes.length + " bytes，耗时: " + serTime + "ms");

        // 反序列化
        long t1 = System.currentTimeMillis();
        Tree3<Channel> deserialized = MyKryoUtil.deserializeObject(bytes, Tree3.class, true);
        long deserTime = System.currentTimeMillis() - t1;
        System.out.println("反序列化: 耗时 " + deserTime + "ms");

        // 验证
        int deserializedSize = deserialized.getAllIdKeyTreeNodeMap().size();
        System.out.println("反序列化后节点数: " + deserializedSize);
        System.out.println("节点数一致性: " + (originalSize == deserializedSize ? "PASS" : "FAIL"));

        // 验证根节点
        System.out.println("根节点数: " + deserialized.getRootTreeNodeList().size());
        for (TreeNode3<Channel> root : deserialized.getRootTreeNodeList()) {
            System.out.println("  根节点: key=" + root.getKey()
                    + "，name=" + root.getValue().getName()
                    + "，子节点数=" + root.getSubs().size());
        }
    }

    //////////////////////////////////////////
    // 工具方法
    //////////////////////////////////////////

    private Tree3<Channel> buildFullTree() {
        List<OneTreeNode3Adapter<Channel>> adapterList = new ArrayList<>();
        for (Channel channel : channelList) {
            ChannelOneTreeNode3Adapter adapter = new ChannelOneTreeNode3Adapter();
            adapter.setSource(channel);
            adapterList.add(adapter);
        }
        return new EveryoneTree3Builder<Channel>()
                .setAdapterList(adapterList)
                .setSortMethod(TreeNode3SortMethod.DESCENDING)
                .setNoParentStrategy(EveryoneTree3Builder.NO_PARENT_STRATEGY_NODE)
                .build();
    }

    private OneTreeNode3Adapter<Channel> toAdapter(Channel channel) {
        ChannelOneTreeNode3Adapter adapter = new ChannelOneTreeNode3Adapter();
        adapter.setSource(channel);
        return adapter;
    }
}

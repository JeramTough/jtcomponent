package test.tree3;

import com.jeramtough.jtcomponent.tree3.adapter.ChildrenLoader3;
import com.jeramtough.jtcomponent.tree3.adapter.OneTreeNode3Adapter;
import com.jeramtough.jtcomponent.tree3.builder.EveryoneTree3Builder;
import com.jeramtough.jtcomponent.tree3.builder.LazyTree3Builder;
import com.jeramtough.jtcomponent.tree3.core.Tree3;
import com.jeramtough.jtcomponent.tree3.core.TreeNode3;
import com.jeramtough.jtcomponent.tree3.filter.ExcludeCodeTreeNode3Filter;
import com.jeramtough.jtcomponent.tree3.filter.TreeNode3Filter;
import com.jeramtough.jtcomponent.tree3.rebuilder.FilterTree3Rebuilder;
import com.jeramtough.jtcomponent.tree3.rebuilder.FromSubTree3Rebuilder;
import com.jeramtough.jtcomponent.tree3.rebuilder.MaxRetainSubTree3Rebuilder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jeramtough.jtcomponent.utils.JtStrUtil;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;
/**
 * <pre>
 *     tree3 使用示例 / 测试用例。
 *
 *     数据：一棵栏目树
 *       100(栏目A)
 *         ├ 110(A-1)
 *         │   └ 111(A-1-1)
 *         └ 120(A-2)
 *       200(栏目B)
 *         └ 210(B-1)
 *
 * Created on 2026/8/30
 * by @author WeiBoWen
 * </pre>
 */
public class Tree3UsageTest {

    //////////////////////////////////////////
    // 1. 全量加载模式：一次性构建整棵树，再快速打开任意节点
    //////////////////////////////////////////

    @Test
    public void fullLoadAndQuickOpen() {
        List<Dept> depts = sampleDepts();
        Tree3<Dept> tree = buildFullTree(depts);

        // 根节点数量
        assertEquals(2, tree.getRootTreeNodeList().size());
        System.out.println("【全量加载】根节点数量 = " + tree.getRootTreeNodeList().size());

        // 快速打开节点：按 key O(1) 查找
        TreeNode3<Dept> node = tree.getTreeNodeByIdKey("111");
        assertNotNull(node);
        assertEquals("A-1-1", node.getValue().getName());
        System.out.println("【快速打开】key=111 -> " + node.getValue().getName());

        // 按 code 查找
        TreeNode3<Dept> byCode = tree.getTreeNodeByCodeKey("code210");
        assertNotNull(byCode);
        assertEquals("B-1", byCode.getValue().getName());
        System.out.println("【按 code 查找】code210 -> " + byCode.getValue().getName());

        // 选中节点，直接取下一级（内存中已存在）
        TreeNode3<Dept> a = tree.getTreeNodeByIdKey("100");
        List<String> nextLevelKeys =
                a.getSubs().stream().map(TreeNode3::getKey).collect(Collectors.toList());
        assertEquals(Arrays.asList("110", "120"), nextLevelKeys);
        System.out.println("【取下一级】100 的子节点 = " + nextLevelKeys);

        // 选中节点，加载所有子节点（全量后代）
        List<String> allDescKeys =
                a.getAllSubs().stream().map(TreeNode3::getKey).collect(Collectors.toList());
        assertEquals(new HashSet<>(Arrays.asList("110", "111", "120")),
                new HashSet<>(allDescKeys));
        System.out.println("【加载全部子节点】100 的后代 = " + allDescKeys);
    }

    @Test
    public void testPathNames(){
        List<Dept> depts = sampleDepts();
        Tree3<Dept> tree = buildFullTree(depts);

        for (TreeNode3<Dept> treeNode3 : tree.getAll()) {
            System.out.println(JtStrUtil.appendByComma(treeNode3.getPathNames()));
        }


    }

    @Test
    public void searchByCondition() {
        Tree3<Dept> tree = buildFullTree(sampleDepts());

        // 按条件搜索全部匹配节点：level == 2
        List<TreeNode3<Dept>> level2 = tree.search(n -> n.getLevel() == 2);
        assertEquals(Arrays.asList("111"), level2.stream().map(TreeNode3::getKey)
                .collect(Collectors.toList()));
        System.out.println("【条件搜索】level==2 的节点 = "
                + level2.stream().map(TreeNode3::getKey).collect(Collectors.toList()));

        // 在某个节点的后代中搜索
        TreeNode3<Dept> a = tree.getTreeNodeByIdKey("100");
        List<TreeNode3<Dept>> matched = a.searchDescendants(n -> "A-2".equals(n.getValue().getName()));
        assertEquals(1, matched.size());
        assertEquals("120", matched.get(0).getKey());
        System.out.println("【后代搜索】100 下名称=A-2 的节点 = "
                + matched.stream().map(TreeNode3::getKey).collect(Collectors.toList()));
    }

    //////////////////////////////////////////
    // 2. 懒加载模式：选中节点才加载它的下一级
    //////////////////////////////////////////

    @Test
    public void lazyLoadNextLevel() {
        List<Dept> depts = sampleDepts();
        Tree3<Dept> tree = buildLazyTree(depts);

        TreeNode3<Dept> a = tree.getTreeNodeByIdKey("100");
        // 尚未加载子节点
        assertFalse(a.isChildrenLoaded());
        System.out.println("【懒加载】加载前 isChildrenLoaded = " + a.isChildrenLoaded());

        // 选中节点 -> 加载下一级子节点
        List<TreeNode3<Dept>> next = a.getSubs();
        assertTrue(a.isChildrenLoaded());
        assertEquals(Arrays.asList("110", "120"),
                next.stream().map(TreeNode3::getKey).collect(Collectors.toList()));
        System.out.println("【懒加载】加载后 isChildrenLoaded = " + a.isChildrenLoaded()
                + "，下一级 = " + next.stream().map(TreeNode3::getKey).collect(Collectors.toList()));

        // 下一级节点继承了加载器，可继续逐层懒加载
        TreeNode3<Dept> a1 = tree.getTreeNodeByIdKey("110");
        assertFalse(a1.isChildrenLoaded());
        assertEquals(Arrays.asList("111"),
                a1.getSubs().stream().map(TreeNode3::getKey).collect(Collectors.toList()));
        System.out.println("【懒加载】110 加载后 isChildrenLoaded = " + a1.isChildrenLoaded()
                + "，下一级 = "
                + a1.getSubs().stream().map(TreeNode3::getKey).collect(Collectors.toList()));
    }

    @Test
    public void lazyLoadAllDescendants() {
        List<Dept> depts = sampleDepts();
        Tree3<Dept> tree = buildLazyTree(depts);

        TreeNode3<Dept> a = tree.getTreeNodeByIdKey("100");
        // 全量加载：选中节点，递归加载它的所有子节点
        List<String> all = a.getAllSubs().stream().map(TreeNode3::getKey)
                .collect(Collectors.toList());
        assertEquals(new HashSet<>(Arrays.asList("110", "111", "120")),
                new HashSet<>(all));
        System.out.println("【懒加载-全量】100 的全部后代 = " + all);
    }

    //////////////////////////////////////////
    // 3. 节点重构
    //////////////////////////////////////////

    @Test
    public void rebuildFromSubTree() {
        Tree3<Dept> tree = buildFullTree(sampleDepts());

        // 以 "110" 的下一级作为新树根 -> 只有 111
        Tree3<Dept> newTree = new FromSubTree3Rebuilder<>(tree)
                .setSubTreeNodeKey("110")
                .rebuild();

        assertEquals(1, newTree.getRootTreeNodeList().size());
        assertEquals("111", newTree.getRootTreeNodeList().get(0).getKey());
        System.out.println("【重构-取子树】110 的下一级作为新根 = "
                + newTree.getRootTreeNodeList().stream().map(TreeNode3::getKey)
                        .collect(Collectors.toList()));
    }

    @Test
    public void rebuildByFilter() {
        Tree3<Dept> tree = buildFullTree(sampleDepts());

        List<TreeNode3Filter> filters = new ArrayList<>();
        filters.add(new ExcludeCodeTreeNode3Filter(null, null, "code120"));

        Tree3<Dept> newTree = new FilterTree3Rebuilder<>(tree)
                .setFilterList(filters)
                .rebuild();

        assertNull(newTree.getTreeNodeByIdKey("120"));
        assertNotNull(newTree.getTreeNodeByIdKey("110"));
        System.out.println("【重构-过滤】排除 code120 后，120 是否存在 = "
                + (newTree.getTreeNodeByIdKey("120") != null)
                + "，110 是否存在 = "
                + (newTree.getTreeNodeByIdKey("110") != null));
    }

    @Test
    public void rebuildByMaxRetainLevel() {
        Tree3<Dept> tree = buildFullTree(sampleDepts());

        // 只保留到 level 1（0 和 1 层），111 在 level 2 被截断
        Tree3<Dept> newTree = new MaxRetainSubTree3Rebuilder<>(tree)
                .setMaxRetainSubNodeLevel(1)
                .rebuild();

        assertEquals(2, newTree.getRootTreeNodeList().size());
        assertNotNull(newTree.getTreeNodeByIdKey("110"));
        assertNull(newTree.getTreeNodeByIdKey("111"));
        System.out.println("【重构-按层级截断】保留到 level1，根节点 = "
                + newTree.getRootTreeNodeList().stream().map(TreeNode3::getKey)
                        .collect(Collectors.toList())
                + "，111 是否被截断 = " + (newTree.getTreeNodeByIdKey("111") == null));
    }

    //////////////////////////////////////////
    // 工具方法
    //////////////////////////////////////////

    private Tree3<Dept> buildFullTree(List<Dept> depts) {
        List<OneTreeNode3Adapter<Dept>> adapters = new ArrayList<>();
        for (Dept dept : depts) {
            DeptAdapter adapter = new DeptAdapter();
            adapter.setSource(dept);
            adapters.add(adapter);
        }
        return new EveryoneTree3Builder<Dept>()
                .setAdapterList(adapters)
                .setNoParentStrategy(EveryoneTree3Builder.NO_PARENT_STRATEGY_NODE)
                .build();
    }

    private Tree3<Dept> buildLazyTree(List<Dept> depts) {
        Map<String, List<Dept>> byParent = new HashMap<>();
        for (Dept dept : depts) {
            byParent.computeIfAbsent(dept.parentId == null ? "" : dept.parentId,
                    k -> new ArrayList<>()).add(dept);
        }

        ChildrenLoader3<Dept> loader=new ChildrenLoader3<Dept>() {
            @Override
            public List<OneTreeNode3Adapter<Dept>> loadChildren(TreeNode3<Dept> parent) {
                List<Dept> children = byParent.getOrDefault(parent.getKey(), new ArrayList<>());
                return children
                        .stream()
                        .map(dept -> toAdapter(dept))
                        .collect(Collectors.toList());
            }
        };


        List<OneTreeNode3Adapter<Dept>> rootAdapters =
                byParent.getOrDefault("", new ArrayList<>()).stream()
                        .map(this::toAdapter)
                        .collect(Collectors.toList());

        return new LazyTree3Builder<Dept>()
                .setRootAdapterList(rootAdapters)
                .setChildrenLoader(loader)
                .build();
    }

    private OneTreeNode3Adapter<Dept> toAdapter(Dept dept) {
        DeptAdapter adapter = new DeptAdapter();
        adapter.setSource(dept);
        return adapter;
    }

    private List<Dept> sampleDepts() {
        return Arrays.asList(
                new Dept("100", null, "栏目A", "code100"),
                new Dept("110", "100", "A-1", "code110"),
                new Dept("111", "110", "A-1-1", "code111"),
                new Dept("120", "100", "A-2", "code120"),
                new Dept("200", null, "栏目B", "code200"),
                new Dept("210", "200", "B-1", "code210"));
    }

    //////////////////////////////////////////
    // 内部类型
    //////////////////////////////////////////

    static class Dept {
        final String id;
        final String parentId;
        final String name;
        final String code;

        Dept(String id, String parentId, String name, String code) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
            this.code = code;
        }

        String getName() {
            return name;
        }
    }

    static class DeptAdapter implements OneTreeNode3Adapter<Dept> {
        private Dept dept;

        @Override
        public void setSource(Object source) {
            this.dept = (Dept) source;
        }

        @Override
        public Dept getValue() {
            return dept;
        }

        @Override
        public String getKey() {
            return dept.id;
        }

        @Override
        public String getParentKey() {
            return dept.parentId;
        }

        @Override
        public String getCode() {
            return dept.code;
        }

        @Override
        public String getName() {
            return dept.name;
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }
}

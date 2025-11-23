package test.tree2;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.jeramtough.jtcomponent.tree2.adpater.FileRootTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.adpater.OneTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.adpater.RootTreeNode2Adapter;
import com.jeramtough.jtcomponent.tree2.builder.EveryoneTree2Builder;
import com.jeramtough.jtcomponent.tree2.builder.RootTree2Builder;
import com.jeramtough.jtcomponent.tree2.builder.rebuilder.FilterTree2Rebuilder;
import com.jeramtough.jtcomponent.tree2.builder.rebuilder.FromSubTree2Rebuilder;
import com.jeramtough.jtcomponent.tree2.builder.rebuilder.MaxRetainSubTree2Rebuilder;
import com.jeramtough.jtcomponent.tree2.core.Tree2;
import com.jeramtough.jtcomponent.tree2.core.TreeNode2;
import com.jeramtough.jtcomponent.tree2.filter.ExcludeCodeTreeNode2Filter;
import com.jeramtough.jtcomponent.tree2.filter.TreeNode2Filter;
import com.jeramtough.jtcomponent.tree2.sort.TreeNode2SortMethod;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;
import test.common.ExcelUtil;
import test.common.MyKryoUtil;
import test.tree2.channel.Channel;
import test.tree2.channel.ChannelOneTreeNodeAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created on 2019/7/11 16:31
 * by @author WeiBoWen
 */
public class Tree2AdapterTest {


    @Test
    public void test() {

        File rootFile = new File(
                "/home/jeramtough/文档/Mine/Work/KPRXKJ/CMS_CMS/heilongjiang/龙剑网/");
        File rootFile2 = new File(
                "/home/jeramtough/文档/Mine/Work/KPRXKJ/CMS_CMS/heilongjiang" +
                        "/工作网服务器信息.png");

        List<RootTreeNode2Adapter<File>> fileRootTreeNode2AdapterList = new ArrayList<>();
        fileRootTreeNode2AdapterList.add(new FileRootTreeNode2Adapter(rootFile));
        fileRootTreeNode2AdapterList.add(new FileRootTreeNode2Adapter(rootFile2));


        RootTree2Builder<File> rootTree2Builder = new RootTree2Builder<>();
        rootTree2Builder.setRoot(true)
                        .setRootAdapterList(fileRootTreeNode2AdapterList);

        Tree2<File> tree2 = rootTree2Builder.build();

        L.debug(tree2.getAll().size());

    }


    /*@Test
    public void test2() {
        DingTalkHttpClient dingTalkHttpClient = new MyDingTalkHttpClient();
        TreeProcessor treeProcessor = new DefaultTreeProcessor();

        DepartmentRootTreeNode2Adapter adapter = new DepartmentRootTreeNode2Adapter(
                dingTalkHttpClient,
                0L);

        Tree2 tree2 = new RootTree2Builder<Long>()
                .setRoot(true)
                .setRootAdapterList(Arrays.asList(adapter))
                .build();

        L.debug(tree2.getAll().size());
    }*/


    @Test
    public void test3() {
        String json = null;
        try {
            json = cn.hutool.core.io.IoUtil.readUtf8(
                    new FileInputStream("/developer/Codes/IdeaCodes/jtcomponent/DOC/channel.json"));
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
                        TreeNode2SortMethod.DESCENDING)
                .setNoParentStrategy(
                        EveryoneTree2Builder.NO_PARENT_STRATEGY_NODE)
                .build();

        byte[] bytes = MyKryoUtil.serialize(tree2, true,true);

        Tree2<Channel> tree21 = MyKryoUtil.deserializeObject(bytes, Tree2.class,true);

        L.arrive();
    }

    @Test
    public void test4() {
        String json = null;
        try {
            json = cn.hutool.core.io.IoUtil.readUtf8(
                    new FileInputStream(
                            "/developer/Codes/IdeaCodes/jtcomponent/DOC/channel.json"));
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


        List<TreeNode2Filter> filterList = new ArrayList<>();
        ExcludeCodeTreeNode2Filter excludeCodeTreeNode2Filter = new ExcludeCodeTreeNode2Filter(
                null, null, "dxal[A-Za-z0-9]+");
        filterList.add(excludeCodeTreeNode2Filter);


        String subTreeNodeKey = "1854819255244505110";
        Tree2<Channel> newTree2 = new FromSubTree2Rebuilder<>(tree2)
                .setSubTreeNodeKey(subTreeNodeKey)
                .rebuild();

        Tree2<Channel> newTree3 = new FilterTree2Rebuilder<>(newTree2)
                .setFilterList(filterList)
                .rebuild();

        L.arrive();
    }

    @Test
    public void test5() {
        String json = null;
        try {
            json = cn.hutool.core.io.IoUtil.readUtf8(
                    new FileInputStream("/home/jeramtough/Temp/channel" +
                            ".json"));
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

        TreeNode2<Channel> treeNode2 = tree2.getTreeNodeByIdKey("1944680670526550018");
        List<TreeNode2Filter> treeNode2FilterList = new ArrayList<>();
        treeNode2FilterList.add(
                new ExcludeCodeTreeNode2Filter(null, null, "[A-Za-z0-9]+_100000[A-Za-z0-9]+"));
        //[A-Za-z0-9]+_10\\d\\d\\d\\d[A-Za-z0-9]+

        List<TreeNode2<Channel>> subs = treeNode2.getSubs(treeNode2FilterList);

        L.info(1944680690084589570L - 1944680689698713602L);
        L.info(1944680690084589570L - 1944680671185055746L);
        L.arrive();
    }

    @Test
    public void test6() {
        String json = null;
        try {
            json = cn.hutool.core.io.IoUtil.readUtf8(
                    new FileInputStream(
                            "/developer/Codes/IdeaCodes/jtcomponent/DOC/channel.json"));
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


        List<TreeNode2Filter> filterList = new ArrayList<>();
        ExcludeCodeTreeNode2Filter excludeCodeTreeNode2Filter = new ExcludeCodeTreeNode2Filter(
                null, null, "dxal[A-Za-z0-9]+");
        filterList.add(excludeCodeTreeNode2Filter);


        String subTreeNodeKey = "1927363713284534273";
        Tree2<Channel> newTree2 = new FromSubTree2Rebuilder<>(tree2)
                .setSubTreeNodeKey(subTreeNodeKey)
                .rebuild();

        Tree2<Channel> newTree3 = new FilterTree2Rebuilder<>(newTree2)
                .setFilterList(filterList)
                .rebuild();


        List<List<TreeNode2<Channel>>> allTreeNodeList =
                newTree3.getAllForLevel();

        List<Map<String, Object>> dataList = new ArrayList<>();
        for (List<TreeNode2<Channel>> treeNode2s : allTreeNodeList) {
            for (TreeNode2<Channel> treeNode2 : treeNode2s) {
                Map<String, Object> data = new HashMap<>();
                String name = treeNode2.getValue().getName();
                data.put("name", name);
                int count = RandomUtil.randomInt(500, 1000);
                int count2 = RandomUtil.randomInt(500, 1000);
                int sum = count;
                if (name.contains("市")) {
                    sum += count2;
                }
                data.put("count", sum);
                dataList.add(data);
            }


        }

        // 定义表头：Map键 -> Excel列名
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("name", "院名");
        headers.put("count", "登录量");

        // 导出文件路径
        String filePath = "/home/jeramtough/Temp/abc/data_export.xlsx"; // Windows 示例

        // 执行导出
        try {
            ExcelUtil.exportToExcel(dataList, headers, filePath);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        L.arrive();
    }

    @Test
    public void test7(){
        String json = null;
        try {
            json = cn.hutool.core.io.IoUtil.readUtf8(
                    new FileInputStream(
                            "/developer/Codes/IdeaCodes/jtcomponent/DOC/channel.json"));
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



        String subTreeNodeKey = "51690614767000186";
        Tree2<Channel> newTree2 = new FromSubTree2Rebuilder<>(tree2)
                .setSubTreeNodeKey(subTreeNodeKey)
                .rebuild();

        Tree2<Channel> newTree3 = new MaxRetainSubTree2Rebuilder<>(newTree2)
                .setMaxRetainSubNodeLevel(3)
                .rebuild();

        L.arrive();
    }

    //**********
    private boolean hasSubs(File file) {
        if (file.isFile()) {
            return false;
        }

        if (file.listFiles() == null) {
            return false;
        }

        return true;
    }

    private List<File> getSubs(File file) {
        return Arrays.asList(Objects.requireNonNull(file.listFiles()));
    }

    private File getParent(File file) {
        File parentFile = file.getParentFile();
       /* if (!"Desktop".equalsIgnoreCase(parentFile.getName())) {
            return parentFile;
        }
        return null;*/
        return parentFile;
    }
}

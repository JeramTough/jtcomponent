package test.tree;

import com.jeramtough.jtcomponent.tree.adapter.BaseRootTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.adapter.FileRootTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.processor.DefaultTreeProcessor;
import com.jeramtough.jtcomponent.tree.processor.TreeProcessor;
import com.jeramtough.jtcomponent.tree.structure.DefaultTreeNode;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtlog.facade.L;
import org.junit.jupiter.api.Test;
import test.tree.component.dingtalk.DepartmentRootTreeNodeAdapter;
import test.tree.component.dingtalk.net.DingTalkHttpClient;
import test.tree.component.dingtalk.net.MyDingTalkHttpClient;

import java.io.File;
import java.util.*;

/**
 * Created on 2019/7/11 16:31
 * by @author WeiBoWen
 */
public class TreeAdapterTest {

    @Test
    public void test1() {
        boolean root = true;

        File startFile = new File("C:\\Users\\weibw\\Desktop\\a\\c");
        LinkedList<File> tempFileLinkedList = new LinkedList<>();

        File rootFile;
        TreeNode rootTreeNode;
        Map<File, TreeNode> keyFileTreeStructureMap = new HashMap<>();


        if (root) {
            rootFile = startFile;
        }
        else {
            for (; ; ) {
                File parentFile = getParent(startFile);
                if (parentFile != null) {
                    startFile = parentFile;
                }
                else {
                    rootFile = startFile;
                    break;
                }
            }
        }


        rootTreeNode = new DefaultTreeNode(rootFile);

        if (hasSubs(rootFile)) {
            List<File> subFileList = getSubs(rootFile);
            for (File file : subFileList) {
                tempFileLinkedList.add(file);

                TreeNode treeNode = new DefaultTreeNode(file);
                rootTreeNode.addSub(treeNode);
                keyFileTreeStructureMap.put(file, treeNode);
            }

            File tempFile;
            while (!tempFileLinkedList.isEmpty()) {

                tempFile = tempFileLinkedList.removeFirst();
                TreeNode treeNode = keyFileTreeStructureMap.get(tempFile);

                if (hasSubs(tempFile)) {
                    List<File> subFileList1 = getSubs(tempFile);
                    for (File file1 : subFileList1) {
                        if (hasSubs(file1)) {
                            tempFileLinkedList.add(file1);
                        }
                        TreeNode treeNode1 = new DefaultTreeNode(file1);
                        keyFileTreeStructureMap.put(file1, treeNode1);
                        treeNode.addSub(treeNode1);
                    }
                }

            }
        }

        L.arrive();

    }


    @Test
    public void test() {

        File rootFile = new File("C:\\Users\\weibw\\Desktop");
        FileRootTreeNodeAdapter adapter = new FileRootTreeNodeAdapter(rootFile);
        TreeProcessor treeProcessor = new DefaultTreeProcessor();
        TreeNode treeNode = treeProcessor.processing(true, adapter);

        String detail = treeNode.getDetail();
        L.debug(detail);

    }


    @Test
    public void test2() {
        DingTalkHttpClient dingTalkHttpClient = new MyDingTalkHttpClient();
        TreeProcessor treeProcessor = new DefaultTreeProcessor();

        BaseRootTreeNodeAdapter adapter = new DepartmentRootTreeNodeAdapter(dingTalkHttpClient, 0L);
        TreeNode treeNode = treeProcessor.processing(true, adapter);

        if (treeNode.getSubs().size() > 0) {
            if (treeNode.getSubs().get(0).getBrothers().size() > 0) {
                L.debug(treeNode.getSubs().get(0).getBrothers().get(0).getDetail());
            }
        }
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

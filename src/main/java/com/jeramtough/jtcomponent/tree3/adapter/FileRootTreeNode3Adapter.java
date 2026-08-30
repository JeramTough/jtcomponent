package com.jeramtough.jtcomponent.tree3.adapter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *     文件系统根节点适配器。
 *
 * Created on 2019/7/12 8:59
 * by @author WeiBoWen
 * </pre>
 */
public class FileRootTreeNode3Adapter implements RootTreeNode3Adapter<File> {

    private final File file;

    public FileRootTreeNode3Adapter(File file) {
        this.file = file;
    }

    @Override
    public File getValue() {
        return this.file;
    }

    @Override
    public String getKey() {
        return getValue().getAbsolutePath();
    }

    @Override
    public File getParent() {
        return getValue().getParentFile();
    }

    @Override
    public List<File> getSubs() {
        File[] files = getValue().listFiles();
        return files == null ? java.util.Collections.<File>emptyList() : Arrays.asList(files);
    }

    @Override
    public boolean hasSubs() {
        if (getValue().isFile()) {
            return false;
        }
        File[] files = getValue().listFiles();
        return files != null && files.length > 0;
    }

    @Override
    public RootTreeNode3Adapter<File> getNewInstance(File value) {
        return new FileRootTreeNode3Adapter(value);
    }
}

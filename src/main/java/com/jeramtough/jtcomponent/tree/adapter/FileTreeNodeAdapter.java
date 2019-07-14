package com.jeramtough.jtcomponent.tree.adapter;

import java.io.File;
import java.util.*;

/**
 * Created on 2019/7/12 8:59
 * by @author WeiBoWen
 */
public class FileTreeNodeAdapter extends BaseTreeNodeAdapter<File> {

    public FileTreeNodeAdapter(File file) {
        super(file);
    }

    @Override
    public File getParent() {
        File parentFile = get().getParentFile();
        return parentFile;
    }

    @Override
    public List<File> getSubs() {
        return Arrays.asList(Objects.requireNonNull(get().listFiles()));
    }

    @Override
    public boolean hasSubs() {
        if (get().isFile()) {
            return false;
        }

        if (get().listFiles() == null) {
            return false;
        }

        return true;
    }

    @Override
    public BaseTreeNodeAdapter<File> getNewInstance(File file) {
        return new FileTreeNodeAdapter(file);
    }
}

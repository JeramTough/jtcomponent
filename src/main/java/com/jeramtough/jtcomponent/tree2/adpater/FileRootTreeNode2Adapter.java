package com.jeramtough.jtcomponent.tree2.adpater;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created on 2019/7/12 8:59
 * by @author WeiBoWen
 */
public class FileRootTreeNode2Adapter implements RootTreeNode2Adapter<File> {

    private final File file;

    public FileRootTreeNode2Adapter(File file) {
        this.file = file;
    }

   /* @Override
    public void setSource(Object source) {
        this.file = (File) source;
    }*/

    /*@Override
    public void setValue(File value) {
        this.file = value;
    }*/

    @Override
    public String getKey() {
        File file = getValue();
        Objects.requireNonNull(file);
        String key = file.getAbsolutePath();
        return key;
    }

    @Override
    public File getParent() {
        File parentFile = getValue().getParentFile();
        return parentFile;
    }

    @Override
    public List<File> getSubs() {
        return Arrays.asList(Objects.requireNonNull(getValue().listFiles()));
    }

    @Override
    public boolean hasSubs() {
        if (getValue().isFile()) {
            return false;
        }

        if (getValue().listFiles() == null) {
            return false;
        }

        return true;
    }

    @Override
    public RootTreeNode2Adapter<File> getNewInstance(File value) {
        FileRootTreeNode2Adapter fileRootTreeNode2Adapter = new FileRootTreeNode2Adapter(value);
        return fileRootTreeNode2Adapter;
    }

    @Override
    public File getValue() {
        return this.file;
    }


}

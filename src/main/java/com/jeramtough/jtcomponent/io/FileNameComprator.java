package com.jeramtough.jtcomponent.io;

import java.io.File;
import java.util.Comparator;

/**
 * <pre>
 * Created on 2021/9/28 下午5:56
 * by @author WeiBoWen
 * </pre>
 */
public class FileNameComprator implements Comparator<File> {

    private final boolean isDesc;

    public FileNameComprator(boolean isDesc) {
        this.isDesc = isDesc;
    }


    @Override
    public int compare(File f1, File f2) {
        String name1 = f1.getName();
        String name2 = f2.getName();

        if (isDesc) {
            // 倒序排序
            return name2.compareTo(name1);
        }
        else {
            // 升序排序
            return name1.compareTo(name2);
        }

    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}

package com.jeramtough.jtcomponent.io;

import java.io.File;
import java.util.Comparator;

/**
 * <pre>
 * Created on 2021/9/28 下午5:56
 * by @author WeiBoWen
 * </pre>
 */
public class FileLastModifiedComprator implements Comparator<File> {
    @Override
    public int compare(File f1, File f2) {
        long diff = f1.lastModified() - f2.lastModified();
        if (diff > 0) {
            //倒序正序控制
            return -1;
        }
        else if (diff == 0) {
            return 0;
        }
        else {
            //倒序正序控制
            return 1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}

package com.jeramtough.jtcomponent.io;


import java.io.File;

/**
 * @author 11718
 * on 2017  November 21 Tuesday 14:24.
 */

public class Directory extends File {

    private Directory(File file) {
        this(file.getAbsolutePath(), false);
    }

    public Directory(String pathname) {
        this(pathname, false);
    }

    private Directory(File file, boolean autoCreate) {
        this(file.getAbsolutePath(), autoCreate);
    }

    public Directory(String pathname, boolean autoCreate) {
        super(pathname);
        if (!this.exists() && autoCreate) {
            this.mkdirs();
        }
        if (!this.isDirectory()) {
            throw new IllegalArgumentException("This path isn't a directory");
        }
    }

    @Override
    public boolean exists() {
        if (isDirectory() && super.exists()) {
            return true;
        }
        else {
            return false;
        }
    }
}

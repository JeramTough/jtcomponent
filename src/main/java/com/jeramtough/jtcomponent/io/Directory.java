package com.jeramtough.jtcomponent.io;


import java.io.File;

/**
 * @author 11718
 * on 2017  November 21 Tuesday 14:24.
 */

public class Directory extends File {

    public Directory(String pathname) {
        super(pathname);
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

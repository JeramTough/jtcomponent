package com.jeramtough.jtcomponent.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created on 2019-08-01 12:37
 * by @author JeramTough
 */
public class FileUtil {

    public static boolean createFile(File file){
        try {
            File parentDirectory = file.getParentFile();
            if (!parentDirectory.exists()) {
                boolean isOk = parentDirectory.mkdirs();
                if (isOk) {
                    return file.createNewFile();
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}

package com.jeramtough.jtcomponent.utils;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created on 2019-08-01 12:37
 * by @author JeramTough
 */
@Deprecated
public class FileUtil {

    public static boolean createFile(File file) {
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

    public static String getContentType(File file) {
        URI uri = file.toURI();
        //利用nio提供的类判断文件ContentType
        Path path = Paths.get(uri);
        String contentType = null;
        try {
            contentType = Files.probeContentType(path);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Read File ContentType Error");
        }
        //若失败则调用另一个方法进行判断
        if (contentType == null) {
            contentType = new MimetypesFileTypeMap().getContentType(new File(uri));
        }
        return contentType;
    }

}

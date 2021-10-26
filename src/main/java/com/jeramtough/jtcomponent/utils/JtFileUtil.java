package com.jeramtough.jtcomponent.utils;

import com.jeramtough.jtcomponent.io.Directory;
import com.jeramtough.jtcomponent.io.FileLastModifiedComprator;
import com.jeramtough.jtcomponent.io.FileNameComprator;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created on 2019-08-01 12:37
 * by @author JeramTough
 */
public class JtFileUtil {

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


    public static List<File> orderByLastModified(Directory directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        Arrays.sort(files, new FileLastModifiedComprator());

        List<File> fileList = new ArrayList<>();
        Collections.addAll(fileList, files);
        return fileList;
    }

    public static List<File> orderByNameAsc(Directory directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        Arrays.sort(files, new FileNameComprator(false));

        List<File> fileList = new ArrayList<>();
        Collections.addAll(fileList, files);
        return fileList;
    }

    public static List<File> orderByNameDesc(Directory directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        Arrays.sort(files, new FileNameComprator(true));

        List<File> fileList = new ArrayList<>();
        Collections.addAll(fileList, files);
        return fileList;
    }

}

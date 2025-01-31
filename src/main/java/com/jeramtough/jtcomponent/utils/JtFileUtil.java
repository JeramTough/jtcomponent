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
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

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


    /**
     * 遍历文件夹及其所有子文件夹，筛选出在指定时间范围内的文件
     *
     * @param dirPath   要遍历的根目录路径
     * @param startDate 起始日期
     * @param endDate   结束日期
     * @return 符合时间范围条件的文件列表
     */
    public static List<File> filterFilesByDateRange(String dirPath, Date startDate, Date endDate) {
        List<File> result = new ArrayList<>();
        File rootDir = new File(dirPath);

        // 如果根目录不存在或不是文件夹，直接返回空列表
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            System.err.println("目录不存在或路径不是一个文件夹: " + dirPath);
            return result;
        }

        // 遍历目录及其子目录
        traverseDirectory(rootDir, startDate, endDate, result);
        return result;
    }

    /**
     * 递归遍历目录及其子目录
     *
     * @param dir       当前目录
     * @param startDate 起始日期
     * @param endDate   结束日期
     * @param result    符合条件的文件列表
     */
    private static void traverseDirectory(File dir, Date startDate, Date endDate, List<File> result) {
        // 获取当前目录下的所有文件和文件夹
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                // 递归处理子目录
                traverseDirectory(file, startDate, endDate, result);
            } else {
                // 检查文件的创建时间或更新时间是否在范围内
                if (isFileWithinDateRange(file, startDate, endDate)) {
                    result.add(file);
                }
            }
        }
    }

    /**
     * 检查文件的创建时间或最后修改时间是否在指定范围内
     *
     * @param file      要检查的文件
     * @param startDate 起始日期
     * @param endDate   结束日期
     * @return true 如果文件在时间范围内，false 否则
     */
    private static boolean isFileWithinDateRange(File file, Date startDate, Date endDate) {
        try {
            // 使用 Files 和 BasicFileAttributes 获取文件的创建时间和修改时间
            BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            long creationTime = attributes.creationTime().toMillis();
            long lastModifiedTime = attributes.lastModifiedTime().toMillis();

            // 检查创建时间或最后修改时间是否在范围内
            return (creationTime >= startDate.getTime() && creationTime <= endDate.getTime())
                    || (lastModifiedTime >= startDate.getTime() && lastModifiedTime <= endDate.getTime());
        } catch (IOException e) {
            System.err.println("无法读取文件属性: " + file.getAbsolutePath());
            return false;
        }
    }
}


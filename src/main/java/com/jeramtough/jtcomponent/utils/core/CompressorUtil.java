package com.jeramtough.jtcomponent.utils.core;


import com.jeramtough.jtcomponent.utils.io.Directory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created on 2018-12-17 09:21
 * by @author JeramTough
 */
public class CompressorUtil {

    /**
     * s
     * 压缩文件
     *
     * @param directory       压缩源文件夹
     * @param destZipFilePath 压缩目的路径
     */
    public static void compress(Directory directory, String destZipFilePath) {
        String srcFilePath = directory.getAbsolutePath();
        File src = new File(srcFilePath);

        if (!src.exists()) {
            throw new RuntimeException(srcFilePath + "不存在");
        }
        File zipFile = new File(destZipFilePath);

        try {

            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            String baseDir = "";
            compressByType(src, zos, baseDir);
            zos.close();

        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 压缩多个文件到指定压缩包，如果file是文件才加入压缩包，如果是文件夹
     * 那就忽略它
     *
     * @param files           files
     * @param destZipFilePath 目的zip文件路径
     */
    public static void compress(File[] files, String destZipFilePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(
                    new File(destZipFilePath));
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (File file : files) {
                if (file.isFile()) {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(
                            new FileInputStream(file));
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    int count;
                    byte[] buf = new byte[1024];
                    while ((count = bufferedInputStream.read(buf)) != -1) {
                        zipOutputStream.write(buf, 0, count);
                    }
                    bufferedInputStream.close();
                }
            }
            zipOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 按照原路径的类型就行压缩。文件路径直接把文件压缩，
     *
     * @param src
     * @param zos
     * @param baseDir
     */
    private static void compressByType(File src, ZipOutputStream zos, String baseDir) {

        if (!src.exists()) {
            return;
        }
//        System.out.println("压缩路径" + baseDir + src.getName());
        //判断文件是否是文件，如果是文件调用compressFile方法,如果是路径，则调用compressDir方法；
        if (src.isFile()) {
            //src是文件，调用此方法
            compressFile(src, zos, baseDir);

        }
        else if (src.isDirectory()) {
            //src是文件夹，调用此方法
            compressDir(src, zos, baseDir);

        }

    }

    /**
     * 压缩文件
     */
    private static void compressFile(File file, ZipOutputStream zos, String baseDir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(baseDir + file.getName());
            zos.putNextEntry(entry);
            int count;
            byte[] buf = new byte[1024];
            while ((count = bis.read(buf)) != -1) {
                zos.write(buf, 0, count);
            }
            bis.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件夹
     */
    private static void compressDir(File dir, ZipOutputStream zos, String baseDir) {
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files != null) {
            if (files.length == 0) {
                try {
                    zos.putNextEntry(new ZipEntry(baseDir + dir.getName() + File.separator));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (File file : files) {
                compressByType(file, zos, baseDir + dir.getName() + File.separator);
            }
        }
    }
}

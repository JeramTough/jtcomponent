package com.jeramtough.jtcomponent.utils;

import com.jeramtough.jtcomponent.io.Directory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class JtIoUtil {
    public static String readTxtFile(String fileUri, String encoding) {
        try {
            File file = new File(fileUri);
            if (file.isFile() && file.exists()) {
                InputStreamReader read =
                        new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuilder strBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    //					System.out.println(line);
                    strBuilder.append(line + "\n");
                }
                read.close();
                return strBuilder.toString();
            }
            else {
                System.out.println("The file isn't exist");
            }
        }
        catch (Exception e) {
            System.out.println("Wrongly read the file");
            e.printStackTrace();
        }
        return null;

    }

    public static Directory getSystemTempDir() {
        String tempDirPath = System.getProperty("java.io.tmpdir");
        return new Directory(tempDirPath);
    }

    public static Directory getSystemTempDir(String inDirName) {
        String tempDirPath = System.getProperty("java.io.tmpdir");
        tempDirPath = tempDirPath + File.separator + inDirName;
        Directory directory = new Directory(tempDirPath, true);
        return directory;
    }

}

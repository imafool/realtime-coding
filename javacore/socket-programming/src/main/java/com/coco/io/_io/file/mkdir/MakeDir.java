package com.coco.io._io.file.mkdir;

import java.io.File;

/**
 * 创建文件夹
 */
public class MakeDir {
    public static final String PATHNAME_DIR = "C:\\Users\\Administrator\\Desktop\\javaiotest\\";

    public static void main(String[] args) {
        mkdir();
        multiDirs();
        // deleted();
    }

    private static void deleted() {
        File file = new File(PATHNAME_DIR + "demo.txt");
        if (file.exists() && file.delete()){
            System.out.println("Deleted!");
        }

        File dir = new File(PATHNAME_DIR + "demo\\");
        if (dir.exists() && dir.delete()){
            System.out.println("Deleted!");
        }
    }

    private static void multiDirs() {
        String suffix = "multi\\ab\\cde\\efgh";
        File file = new File(PATHNAME_DIR + suffix);
        if (!file.exists() && file.mkdirs()){
            System.out.println("Success");
        }else{
            System.out.println("Already exsit or Failure");
        }
    }

    private static void mkdir() {
        String suffix = "dir";
        File file = new File(PATHNAME_DIR + suffix);
        if (!file.exists() && file.mkdir()){
            System.out.println("Success");
        }else{
            System.out.println("Already exsit or Failure");
        }
    }


}

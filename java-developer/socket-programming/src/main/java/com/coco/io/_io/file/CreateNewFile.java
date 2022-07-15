package com.coco.io._io.file;

import java.io.File;
import java.io.IOException;

/**
 * 文件：存储数据
 *
 * 程序 <--输入流-- 文件 （读取外部数据到程序中）
 * 程序 --输出流--> 文件 （将程序数据输出到外部存储设备或网络）
 */
public class CreateNewFile {

    public static final String PATHNAME_DIR = "C:\\Users\\Administrator\\Desktop\\javaiotest\\";

    public static final String PATHNAME = "C:\\Users\\Administrator\\Desktop\\javaiotest\\readme.txt";

    public static void main(String[] args) throws IOException {
        //创建文件
        createNewFile();
        //创建文件
        createNewFileWithParent();
    }

    private static void createNewFileWithParent() throws IOException {
        String filename = "markdown.md";
        File file = new File(PATHNAME_DIR, filename);
        printInfo(file);
    }

    private static void createNewFile() throws IOException {
        File file = new File(CreateNewFile.PATHNAME);
        printInfo(file);
    }

    private static void printInfo(File file) throws IOException {
        if (!file.exists() && file.createNewFile()){
            System.out.println("文件创建成功...");
            return;
        }
        System.out.println("文件已存在或者创建失败...");
    }
}


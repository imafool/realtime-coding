package com.coco.io._io.file;

import java.io.File;

/**
 * 获取File相关信息
 */
public class GetFileInfo {
    public static void main(String[] args) {
        String pathname = "C:\\Users\\Administrator\\Desktop\\javaiotest\\readme.txt";
        File file = new File(pathname);
        getFileInfo(file);
    }

    private static void getFileInfo(File file) {
        //文件是否存在
        System.out.println(file.exists());
        //文件名
        System.out.println(file.getName());
        //绝对路径
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getAbsolutePath());
        //文件大小(Byte)
        System.out.println(file.length());
        //父File
        System.out.println(file.getPath());
        System.out.println(file.getParent());
        System.out.println(file.getParentFile());
        //文件还是目录（目录是一种特殊的文件）
        System.out.println(file.isDirectory());
        File file1 = new File(file.getParent());
        System.out.println(file1.isDirectory());
    }
}

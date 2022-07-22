package com.coco.io._io.iostream.outputstream;

import java.io.*;

/**
 * 文件拷贝：字节方式 [磁盘 - 输入 - 程序 - 输出 - 磁盘]，由于效率，使用字节数组方式，分别拷贝文本，和mp4文件
 */
public class _CopyFile {
    private static final String BASE = "C:\\Users\\Administrator\\Desktop\\javaiotest\\";

    public static void main(String[] args) throws IOException {
        copyText();
        copyMp4();
    }

    private static void copyMp4() throws IOException {
        String filename = "zeng.mp4";
        String filename_copy = "zeng_copy.mp4";
        InputStream is = new FileInputStream(new File(BASE + filename));
        OutputStream os = new FileOutputStream(new File(BASE + filename_copy));
        byte[] bytes = new byte[1024];
        while(is.read(bytes) != -1){
            os.write(bytes);
        }
        os.close();
        is.close();
        System.out.println(filename_copy + " Copy completed!");
    }

    private static void copyText() throws IOException {
        String filename = "readme.txt";
        String filename_copy = "readme_copy.txt";
        InputStream is = new FileInputStream(new File(BASE + filename));
        OutputStream os = new FileOutputStream(new File(BASE + filename_copy));
        byte[] bytes = new byte[1024];
        while(is.read(bytes) != -1){
            os.write(bytes);
        }
        os.close();
        is.close();
        System.out.println(filename_copy + " Copy completed!");
    }
}

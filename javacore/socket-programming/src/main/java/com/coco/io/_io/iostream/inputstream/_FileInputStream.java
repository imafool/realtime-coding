package com.coco.io._io.iostream.inputstream;

import java.io.*;

//字节方式读取磁盘文件 C:\Users\Administrator\Desktop\javaiotest\readme.txt 18384KB ASCII
public class _FileInputStream {
    private static final String PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\readme.txt";

    public static void main(String[] args) throws IOException {
        // readByte();
        readByteArray();
    }

    private static void readByteArray() throws IOException {
        long start = start();

        InputStream is = new FileInputStream(new File(PATH));
        //实际读取的字节数
        int readLen = 0;
        byte buf[] = new byte[1024];
        while((readLen = is.read(buf)) != -1){
            System.out.print(new String(buf, 0, readLen));
        }
        is.close();
        System.out.println("-- End --");

        long end = end();
        used_time(end, start);
    }

    private static void readByte() throws IOException {
        long start = start();

        InputStream is = new FileInputStream(new File(PATH));
        int c;
        while((c = is.read()) != -1){
            System.out.print((char) c);
        }
        is.close();
        System.out.println("-- End --");

        long end = end();
        used_time(end, start);
    }

    //*********************************

    private static long start(){
        return System.currentTimeMillis();
    }

    private static long end(){
        return System.currentTimeMillis();
    }

    private static void used_time(long end, long start){
        System.out.println("===================== 用时：" + (end - start) / 1000 + " 秒，" + (end - start) + " 毫秒，=====================");
    }
}

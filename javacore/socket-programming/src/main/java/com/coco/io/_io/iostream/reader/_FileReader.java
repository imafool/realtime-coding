package com.coco.io._io.iostream.reader;

import java.io.*;
import java.util.Arrays;

/**
 * 字符输入:
 */
public class _FileReader {
    private static final String PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\shaw\\shaw-zh-en.txt";

    public static void main(String[] args) throws IOException {
        readChar();
        readCharArray();
    }

    private static void readCharArray() throws IOException {
        long start = start();

        File file = new File(PATH);
        Reader reader = new FileReader(file);
        char[] cbuf = new char[1024];
        while (reader.read(cbuf) != -1){
            System.out.print(cbuf);
        }

        System.out.println();
        reader.close();

        long end = end();
        used_time(end, start);
    }

    private static void readChar() throws IOException {
        long start = start();

        File file = new File(PATH);
        Reader reader = new FileReader(file);
        int c;
        while((c = reader.read()) != -1){
            System.out.print((char)c);
        }
        System.out.println();
        reader.close();

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

package com.coco.io._io.iostream.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 使用缓冲写数据到文件
 */
public class _BufferedWriter {

    private static final String PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\buffer.txt";

    public static void main(String[] args) throws IOException {
        bufferWrite();
    }

    private static void bufferWrite() throws IOException {
        File file = new File(PATH);
        FileWriter writer = new FileWriter(file, Boolean.TRUE);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.newLine();
        bw.write('A');
        bw.newLine();
        bw.write("这里换行了，你说神奇补肾气？！");
        bw.newLine();
        bw.write("XXX---XXX---ZZZ");
        bw.flush();
        bw.close();
        System.out.println("Write Completed!");
    }


}

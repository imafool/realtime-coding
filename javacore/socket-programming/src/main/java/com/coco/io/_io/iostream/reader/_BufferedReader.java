package com.coco.io._io.iostream.reader;

import java.io.*;

/**
 * 字符流+缓冲，读取文本
 */
public class _BufferedReader {
    private static final String PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\shaw\\shaw-zh-en.txt";

    public static void main(String[] args) throws IOException {
        bufferRead();
        bufferReadArray();
        bufferReadLine();
    }

    private static void bufferReadLine() throws IOException {
        long start = start();

        File file = new File(PATH);
        Reader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String line;
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
        br.close();//包装流关闭，内部负责关闭节点流

        long end = end();
        used_time(end, start);
    }

    private static void bufferReadArray() throws IOException {
        long start = start();

        File file = new File(PATH);
        Reader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        char[] cbuf = new char[1024];
        while(br.read(cbuf) != -1){
            System.out.print(String.valueOf(cbuf));
        }
        System.out.println();
        br.close();//包装流关闭，内部负责关闭节点流

        long end = end();
        used_time(end, start);
    }

    private static void bufferRead() throws IOException {
        long start = start();

        File file = new File(PATH);
        Reader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        int c;
        while((c = br.read()) != -1){
            System.out.print((char)c);
        }
        System.out.println();
        br.close();//包装流关闭，内部负责关闭节点流

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

package com.coco.io._io.iostream._transform;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 字节流和字符流的转换
 */
public class _InputStreamReader {
    private static final String PATH1 = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\demo-utf-8.txt";

    private static final String PATH2 = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\demo-ansi.txt";

    public static void main(String[] args) throws IOException {
        utf8Read();
        ansiRead();
    }

    private static void ansiRead() throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(PATH2), "GBK");
        int c;
        while((c = isr.read()) != -1){
            System.out.print((char) c);
        }
        System.out.println();
        isr.close();
    }

    private static void utf8Read() throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(PATH1), StandardCharsets.UTF_8);
        int c;
        while((c = isr.read()) != -1){
            System.out.print((char) c);
        }
        System.out.println();
        isr.close();
    }
}

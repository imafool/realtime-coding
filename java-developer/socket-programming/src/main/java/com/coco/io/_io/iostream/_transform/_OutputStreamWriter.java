package com.coco.io._io.iostream._transform;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * 字节流和字符流的转换
 */
public class _OutputStreamWriter {
    private static final String PATH1 = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\utf.txt";

    private static final String PATH2 = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\gbk.txt";

    public static void main(String[] args) throws IOException {
        utf();
        gbk();
    }

    private static void gbk() throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(PATH2), "GBK");
        osw.write("噫嘻：He，Tui！");
        osw.flush();
        osw.close();
        System.out.println("Completed!");
    }

    private static void utf() throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(PATH1), StandardCharsets.UTF_8);
        osw.write("噫嘻：He，Tui！");
        osw.flush();
        osw.close();
        System.out.println("Completed!");
    }
}

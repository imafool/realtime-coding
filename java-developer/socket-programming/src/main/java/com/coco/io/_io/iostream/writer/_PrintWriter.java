package com.coco.io._io.iostream.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 打印流
 */
public class _PrintWriter {
    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\xx.txt"), Boolean.TRUE);
        writer.write("xxxx");
        writer.close();
    }
}

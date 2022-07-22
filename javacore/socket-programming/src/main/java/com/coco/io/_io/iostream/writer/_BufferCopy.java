package com.coco.io._io.iostream.writer;

import java.io.*;

/**
 * 使用包装流拷贝
 */
public class _BufferCopy {
    private static final String PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\buffer.txt";

    private static final String COPY_PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\buffer_copy.txt";

    public static void main(String[] args) throws IOException {
        bufferCopy();
    }

    private static void bufferCopy() throws IOException {
        FileReader read = new FileReader(PATH);
        BufferedReader br = new BufferedReader(read);
        FileWriter writer = new FileWriter(COPY_PATH);
        BufferedWriter bw = new BufferedWriter(writer);
        String line;
        while((line = br.readLine()) != null){
            bw.write(line);
            bw.newLine();
        }
        bw.close();
        br.close();
        System.out.println("Copied Completed!");
    }

}

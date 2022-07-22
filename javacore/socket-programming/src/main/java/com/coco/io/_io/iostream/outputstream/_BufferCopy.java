package com.coco.io._io.iostream.outputstream;

import java.io.*;

/**
 * 二进制的Buffer输入
 */
public class _BufferCopy {
    private static final String PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\zeng.mp4";

    private static final String COPY_PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\zeng-copy.mp4";

    public static void main(String[] args) throws IOException {
        bufferCopy();
    }

    private static void bufferCopy() throws IOException {
        FileInputStream fis = new FileInputStream(PATH);
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(COPY_PATH);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        byte[] bbuf = new byte[1024];
        int readLen;
        while((readLen = bis.read(bbuf)) != -1){
            bos.write(bbuf, 0, readLen);
        }
        bos.flush();

        bos.close();
        bis.close();
        System.out.println("Copied!");
    }
}

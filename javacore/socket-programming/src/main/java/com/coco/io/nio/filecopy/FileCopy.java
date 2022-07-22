package com.coco.io.nio.filecopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileCopy {
    public static void main(String[] args) throws IOException {
        fastCopy("C:\\Users\\Administrator\\Desktop\\test\\nio.txt",
                "C:\\Users\\Administrator\\Desktop\\test\\nio_copy.txt");
    }

    public static void fastCopy(String src, String dist) throws IOException {
        FileInputStream fis = new FileInputStream(src); //流
        FileChannel fc = fis.getChannel(); //通道

        FileOutputStream fos = new FileOutputStream(dist); //流
        FileChannel fcout = fos.getChannel(); //通道

        ByteBuffer buffer = ByteBuffer.allocateDirect(1024); //缓冲区

        while (true) {
            int r = fc.read(buffer); //输入通道写入缓冲区
            if (r == -1) {
                break;
            }
            buffer.flip(); //切换读写
            fcout.write(buffer); //缓冲区写出
            buffer.clear(); //清空缓冲区
        }
    }
}
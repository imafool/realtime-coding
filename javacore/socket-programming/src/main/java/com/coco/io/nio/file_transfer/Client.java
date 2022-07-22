package com.coco.io.nio.file_transfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        //3个线程
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    //channel
                    SocketChannel socketChannel = SocketChannel.open();
                    socketChannel.socket().connect(new InetSocketAddress("127.0.0.1", 8888));
                    File file = new File("C:\\Users\\Administrator\\Desktop\\test\\nio.txt");
                    FileChannel fileChannel = new FileInputStream(file).getChannel();
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    socketChannel.read(buffer);
                    buffer.flip();
                    System.out.println(new String(buffer.array(), 0, buffer.limit(), StandardCharsets.UTF_8));
                    buffer.clear();
                    int num;
                    while ((num=fileChannel.read(buffer)) > 0) {
                        buffer.flip();
                        socketChannel.write(buffer);
                        buffer.clear();
                    }
                    if (num == -1) {
                        fileChannel.close();
                        socketChannel.shutdownOutput();
                    }
                    // 接受服务器
                    socketChannel.read(buffer);
                    buffer.flip();
                    System.out.println(new String(buffer.array(), 0, buffer.limit(), StandardCharsets.UTF_8));
                    buffer.clear();
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }
        Thread.yield();
    }
}
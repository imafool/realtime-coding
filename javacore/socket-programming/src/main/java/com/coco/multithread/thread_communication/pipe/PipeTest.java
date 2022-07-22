package com.coco.multithread.thread_communication.pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeTest {
    public static void main(String[] args) throws IOException {
        PipedInputStream inputStream = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream();

        //i/o 连接
        inputStream.connect(outputStream);

        new Thread(() -> {
            try {
                writeData(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                readData(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
    //向管道流中写入数据
    public static void writeData(PipedOutputStream out) throws IOException {
        for (int i = 0; i < 100; i++) {
            String data = "-" + i;
            out.write(data.getBytes());
        }
        out.close();
    }

    //从管道中读取数据
    public static void readData(PipedInputStream input) throws IOException {
        byte[] bytes = new byte[1024];
        int len = input.read(bytes);
        while(len != -1){
            System.out.println(new String(bytes,0,len));
            len = input.read(bytes);
        }
        input.close();
    }
}
package com.coco.io.bio.pool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.concurrent.CountDownLatch;

/**
 * 同非池化客户端
 */
public class BioClientPooled {
    public static void main(String[] args) {
        int clientNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(clientNumber);

        //分别开始启动这20个客户端,并发访问
        for (int index = 0; index < clientNumber; index++, countDownLatch.countDown()) {
            new Thread(new BioClientPooledThread(countDownLatch, index)).start();
        }
    }
}

@SuppressWarnings("all")
class BioClientPooledThread implements Runnable {
    private final CountDownLatch countDownLatch;

    private final Integer clientIndex; //这个线程的编号

    //countDownLatch是java提供的同步计数器。当计数器数值减为0时，所有受其影响而等待的线程将会被激活。这样保证模拟并发请求的真实性
    public BioClientPooledThread(CountDownLatch countDownLatch , Integer clientIndex) {
        this.countDownLatch = countDownLatch;
        this.clientIndex = clientIndex;
    }

    @Override
    public void run() {
        Socket socket;
        OutputStream clientRequest = null;
        InputStream clientResponse = null;

        try {
            socket = new Socket("localhost",8083);
            clientRequest = socket.getOutputStream();
            clientResponse = socket.getInputStream();
            this.countDownLatch.await(); //完成所有线程启动，所有线程一起发送请求
            clientRequest.write(("这是第" + this.clientIndex + " 个客户端的请求。 over").getBytes());
            clientRequest.flush();
            System.out.println("第" + this.clientIndex + "个客户端的请求发送完成，等待服务器返回信息");
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            int realLen;
            String message = "";
            //程序执行到这里，会一直等待服务器返回信息（注意，前提是in和out都不能close，如果close了就收不到服务器的反馈了）
            while((realLen = clientResponse.read(contextBytes, 0, maxLen)) != -1) {
                message += new String(contextBytes , 0 , realLen);
            }
            message = URLDecoder.decode(message, "UTF-8");
            System.out.println("第" + this.clientIndex + "个客户端接收到来自服务器的信息:" + message);
        } catch (Exception e) {

        } finally {
            try {
                if(clientRequest != null) {
                    clientRequest.close();
                }
                if(clientResponse != null) {
                    clientResponse.close();
                }
            } catch (IOException e) {

            }
        }
    }
}
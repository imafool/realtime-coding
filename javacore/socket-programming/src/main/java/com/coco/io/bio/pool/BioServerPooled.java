package com.coco.io.bio.pool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServerPooled {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(60);

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            int port = 8083;
            System.out.println("监听来自于"+ port +"的端口信息");
            serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept(); //TODO 改变不了accept()只能一个一个接受socket的并被阻塞的情况
                BioServerPooledThread socketServerThreadPool = new BioServerPooledThread(socket); //伪异步IO模型，搞一个线程加入到线程池中执行，但是关键的accept()和read()并不受影响，所以说和不用线程池去执行是一致的！
                executorService.execute(socketServerThreadPool);
            }
        } catch(Exception e) {

        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class BioServerPooledThread implements Runnable {
    private final Socket socket;

    public BioServerPooledThread (Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            int sourcePort = socket.getPort();
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            int realLen = in.read(contextBytes, 0, maxLen); // TODO read方法处同样会被阻塞，直到操作系统有数据准备好
            String message = new String(contextBytes , 0 , realLen);
            System.out.println("服务器收到来自于端口：" + sourcePort + "的信息：" + message);
            out.write("回发响应信息！".getBytes());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
                if(this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
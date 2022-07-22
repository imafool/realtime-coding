package com.coco.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 1. 服务端是一个一个的接收客户端请求，处理可以多线程
 * 2. linux系统内，可以创建的线程数有限，虽然可以修改，但是线程越多，CPU切换所需要的时间越长，处理业务的时间越少
 * 3. 如果应用大量使用长连接，线程不会关闭，系统资源消耗容易失控
 */
public class BioServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            int DEFAULT_PORT = 8083;
            System.out.println("监听来自于"+ DEFAULT_PORT +"的端口信息");
            serverSocket = new ServerSocket(DEFAULT_PORT);
            while(true) {
                //接受一个请求socket，开启一个线程处理
                Socket socket = serverSocket.accept(); //OS阻塞
                // TODO 阻塞：服务端只能等接收完一个客户端的请求之后，才能接收下一个客户端的请求
                new Thread(new BioServerThread(socket)).start();
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

class BioServerThread implements Runnable {
    private final Socket socket;

    public BioServerThread (Socket socket) {
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
            int realLen = in.read(contextBytes, 0, maxLen); //OS同样阻塞
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
package com.coco.net.tcp_caseA_byteway;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端接收单客户端消息(字节流方式)
 */
public class Server {
    public static void main(String[] args) throws IOException {
        //监听9999端口
        ServerSocket listener = new ServerSocket(9999);
        //没有客户端连接该端口，程序会阻塞，等待连接，拿到Socket
        //TODO 可以通过accept获取多个客户端Socket，也就是并发
        Socket socket = listener.accept();
        //拿到和该Socket关联的输出流
        InputStream inputStream = socket.getInputStream();
        int c;
        while ((c = inputStream.read()) != -1){
            System.out.print((char)c);
        }
        System.out.println();
        inputStream.close();
        socket.close();
        listener.close();
    }
}

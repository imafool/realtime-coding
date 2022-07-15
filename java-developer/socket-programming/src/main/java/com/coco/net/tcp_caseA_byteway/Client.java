package com.coco.net.tcp_caseA_byteway;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端发送消息(字节流方式)
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //Socket
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        //和Socket对象关联的输出流对象
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello, server!".getBytes());
        outputStream.close();
        socket.close();
        System.out.println("发送完毕");
    }
}

package com.coco.net.tcp_caseB_byteway;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端发送消息，接收服务端返回的消息(字节流方式)
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("-------Client-------");
        //Socket
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        //向服务端写数据
        OutputStream outputStream = socket.getOutputStream();
        String toServerMsg = "hello, server, I am fool!";
        outputStream.write(toServerMsg.getBytes());
        //结束标记
        socket.shutdownOutput();
        //读取服务端回送数据
        InputStream inputStream = socket.getInputStream();
        int readLen;
        byte[] bytes = new byte[1024];
        while((readLen = inputStream.read(bytes)) != -1){
            System.out.println(new String(bytes, 0, readLen));
        }
        //结束标记
        socket.shutdownInput();
        //关闭资源
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}

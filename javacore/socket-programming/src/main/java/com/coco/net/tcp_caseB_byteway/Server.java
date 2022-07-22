package com.coco.net.tcp_caseB_byteway;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端接收单客户端消息，回送/应答单客户端(字节流方式)
 */
public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("-------ServerService-------");
        //监听端口
        ServerSocket serverSocket = new ServerSocket(9999);
        //获取客户端socket
        Socket socket = serverSocket.accept();
        //读取客户端发送的消息
        int readLen;
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        while ((readLen = inputStream.read(bytes)) != -1){
            System.out.println(new String(bytes, 0, readLen));
        }
        //结束标记
        socket.shutdownInput();
        //回送消息到客户端
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("Ai, ei, 你们也好! :)".getBytes());
        //结束标记
        socket.shutdownOutput();
        //关闭资源
        outputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}

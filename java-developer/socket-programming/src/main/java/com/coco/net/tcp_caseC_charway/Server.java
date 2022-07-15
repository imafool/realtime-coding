package com.coco.net.tcp_caseC_charway;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端接收单客户端消息，回送/应答单客户端(字符流方式)
 */
public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("-------ServerService-------");
        //监听端口
        ServerSocket serverSocket = new ServerSocket(9999);
        //获取客户端socket
        Socket socket = serverSocket.accept();

        //读取客户端发送的消息(字符方式)
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println(bufferedReader.readLine());

        //回送消息到客户端(字符方式)
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("Ai, ei, 你们也好! :)");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        //关闭资源
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
        serverSocket.close();
    }
}

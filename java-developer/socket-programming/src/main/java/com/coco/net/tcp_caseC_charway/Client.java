package com.coco.net.tcp_caseC_charway;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端发送消息，接收服务端返回的消息(字符流方式)
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("-------Client-------");
        //Socket
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        //向服务端写数据(字符流方式)
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("hello, server, I am fool!");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        //读取服务端回送数据(字符流方式)
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println(bufferedReader.readLine());

        //关闭资源
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    }
}

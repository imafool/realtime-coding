package com.coco.net._homework.caseA;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端接收客户端发送信息，如果是：nova，返回：我是nova，如果是：hobby，返回：写程序，如果是：其他，返回：我不明白你的意思
 */
public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("-------ServerService-------");
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();

        receiveSend(socket);
        receiveSend(socket);
        receiveSend(socket);

        // socket.close();
        // serverSocket.close();
    }

    private static void receiveSend(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String receiveMsg = bufferedReader.readLine();
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        if ("nova".equals(receiveMsg)){
            bufferedWriter.write("我是nova！");
            bufferedWriter.newLine();
        }else if ("hobby".equals(receiveMsg)){
            bufferedWriter.write("写程序！");
            bufferedWriter.newLine();
        }else{
            bufferedWriter.write("我不明白你的意思！");
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
        // bufferedWriter.close();
        // bufferedReader.close();
    }
}

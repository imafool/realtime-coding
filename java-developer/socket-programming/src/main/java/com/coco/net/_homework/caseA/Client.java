package com.coco.net._homework.caseA;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端发送：name，hobby，其他，接收服务端应答（字符流方式）
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("------Client------");
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);

        sendReceive(socket, "nova");
        sendReceive(socket, "hobby");
        sendReceive(socket, "what the fuck?");

        // socket.close();
    }

    private static void sendReceive(Socket socket, String sendMsg) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(sendMsg);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String msg = bufferedReader.readLine();
        System.out.println(msg);

        // bufferedReader.close();
        // bufferedWriter.close();
    }
}

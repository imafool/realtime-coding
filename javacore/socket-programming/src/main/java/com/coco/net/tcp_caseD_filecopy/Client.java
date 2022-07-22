package com.coco.net.tcp_caseD_filecopy;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端：连接服务器传送一段视频，收到服务端成功接收应答，关闭
 */
public class Client {
    public static void main(String[] args) throws IOException {

        System.out.println("-------Client-------");

        String filepath = "D:\\imafool\\ijava\\java-net\\src\\main\\java\\com\\coco\\tcp_caseD_filecopy\\video.mp4";

        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);

        //(读取本地，写入网络)
        OutputStream outputStream = socket.getOutputStream();
        BufferedInputStream localInput = new BufferedInputStream(new FileInputStream(filepath));
        byte[] bytes = new byte[1024];
        int readLen;
        while ((readLen = localInput.read(bytes)) != -1){
            outputStream.write(bytes, 0, readLen);
        }
        socket.shutdownOutput();

        //接收成功通知
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println(bufferedReader.readLine());

        inputStream.close();
        localInput.close();
        outputStream.close();
        socket.close();
    }
}

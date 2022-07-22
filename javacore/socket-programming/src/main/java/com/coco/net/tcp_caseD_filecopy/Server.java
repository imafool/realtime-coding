package com.coco.net.tcp_caseD_filecopy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端：监听9999端口，接收客户端发来的视频，保存到src下，再发送“收到视频文件”，退出
 */
public class Server {
    public static void main(String[] args) throws IOException {

        System.out.println("-------ServerService-------");

        String filepath = "D:\\imafool\\ijava\\java-net\\src\\main\\java\\com\\coco\\_copy\\video_copy.mp4";

        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();

        //(读取网络，写入本地)
        InputStream inputStream = socket.getInputStream();
        BufferedOutputStream localOutput = new BufferedOutputStream(new FileOutputStream(filepath));
        byte[] bytes = new byte[1024];
        int readLen;
        while ((readLen = inputStream.read(bytes)) != -1){
            localOutput.write(bytes, 0, readLen);
        }
        socket.shutdownInput();

        //发送成功通知
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("我已收到视频文件");
        bufferedWriter.newLine();
        bufferedWriter.flush();
        System.out.println("已发送通知");

        bufferedWriter.close();
        outputStream.close();
        localOutput.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}

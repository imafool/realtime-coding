package com.coco.net._homework.caseC;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收客户端发来的文件名，返回相应的音乐文件,否则返回默认文件
 */
public class Server {
    public static void main(String[] args) throws IOException {

        System.out.println("-------ServerService-------");

        //接收客户端发来的名称，判断
        ServerSocket serverSocket = new ServerSocket(5555);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String filename = bufferedReader.readLine();

        System.out.println("-------------------------------------收到请求");

        File basepath = new File("D:\\imafool\\ijava\\java-net\\src\\main\\java\\com\\coco\\_homework\\caseC\\serverdata");
        File filepath;
        if ("cetlisten".equals(filename)){
            filepath = new File(basepath, "cetlisten.mp3");
        }else{
            filepath = new File(basepath, "default.mp3");
        }

        System.out.println("-------------------------------------获取文件");

        System.out.println("-------------------------------------正在传输");

        //将本地文件或者提示信息发送给客户端
        OutputStream outputStream = socket.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filepath));
        byte[] bytes = new byte[1024];
        int readLen;
        while((readLen = bis.read(bytes)) != -1){
            outputStream.write(bytes, 0, readLen);
        }
        System.out.println("-------------------------------------传输完毕");

        bis.close();
        outputStream.close();
        bufferedReader.close();
        socket.close();
        serverSocket.close();
    }
}

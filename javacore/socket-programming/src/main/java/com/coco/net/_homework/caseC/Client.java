package com.coco.net._homework.caseC;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 输入一个音乐文件名，等待服务端返回该音乐文件，客户端收到该文件，保存到本地
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("-------Client-------");

        System.out.println("-------------------------------------等待输入");

        //输入文件名，发送到服务端
        Scanner scanner = new Scanner(System.in);
        String keyInput = scanner.nextLine();

        System.out.println("-------------------------------------输入完毕，发送请求");

        Socket socket = new Socket(InetAddress.getLocalHost(), 5555);
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(keyInput);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        System.out.println("-------------------------------------请求完毕，等待响应");
        System.out.println("-------------------------------------获取响应");

        //接收服务端返回（mp3 file）文件保存到本地
        String filepath = "D:\\imafool\\ijava\\java-net\\src\\main\\java\\com\\coco\\_homework\\caseC\\clientdata\\download.mp3";
        BufferedOutputStream localOutput = new BufferedOutputStream(new FileOutputStream(filepath));
        InputStream inputStream = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        byte[] bytes = new byte[1024];
        int readLen;
        while ((readLen = bis.read(bytes)) != -1){
            localOutput.write(bytes, 0, readLen);
        }

        System.out.println("-------------------------------------响应完毕");

        bis.close();
        localOutput.close();
        bufferedWriter.close();
        socket.close();
        scanner.close();
    }
}

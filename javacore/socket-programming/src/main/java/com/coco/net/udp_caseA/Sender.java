package com.coco.net.udp_caseA;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * 数据发送端（也可以接收数据）
 */
public class Sender {
    public static void main(String[] args) throws IOException {
        System.out.println("---------Sender----------");
        //在8888接口接收数据
        DatagramSocket datagramSocket = new DatagramSocket(8888);
        //byte buf[], int offset, int length, InetAddress address, int port)
        String msg = "No Chinese?";
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(StandardCharsets.UTF_8), 0, msg.length(), InetAddress.getLocalHost(), 9999);
        System.out.println("开始发送数据...");
        datagramSocket.send(datagramPacket);
        System.out.println("------------数据发送完毕");
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket1 = new DatagramPacket(bytes, bytes.length);
        System.out.println("开始接收接收者应答...");
        datagramSocket.receive(datagramPacket1);
        System.out.println("------------收到应答数据包，开始拆包..");
        byte[] getData = datagramPacket.getData();
        int offset = datagramPacket.getOffset();
        int length = datagramPacket.getLength();
        String data = new String(getData, offset, length);
        InetAddress address = datagramPacket1.getAddress();
        String hostAddress = address.getHostAddress();
        int port = datagramPacket1.getPort();
        //----拆包结果
        System.out.println("收到的应答数据：" + data);
        System.out.println("接收者主机：" + hostAddress);
        System.out.println("接收者端口：" + port);
    }
}

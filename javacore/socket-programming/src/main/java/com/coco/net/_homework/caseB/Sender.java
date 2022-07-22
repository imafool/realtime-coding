package com.coco.net._homework.caseB;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * 发送端发送问题：新的四大名著有哪些？
 */
public class Sender {
    public static void main(String[] args) throws IOException {
        System.out.println("--------Sender--------");
        //发送
        DatagramSocket datagramSocket = new DatagramSocket(7777);
        String sendMsg = "What are the new four great Classics?";
        DatagramPacket datagramPacket = new DatagramPacket(sendMsg.getBytes(StandardCharsets.US_ASCII), 0, sendMsg.length(), InetAddress.getLocalHost(), 6666);
        datagramSocket.send(datagramPacket);

        //接收
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket1 = new DatagramPacket(bytes, bytes.length);
        datagramSocket.receive(datagramPacket1);
        byte[] data = datagramPacket1.getData();
        int offset = datagramPacket1.getOffset();
        int length = datagramPacket1.getLength();
        System.out.println("receive A: " + new String(data, offset, length));

        datagramSocket.close();
    }
}

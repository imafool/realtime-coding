package com.coco.net.udp_caseA;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * 数据接收端
 */
public class Receiver {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------Receiver------------");
        DatagramSocket datagramSocket = new DatagramSocket(9999);
        //一个UDP数据包最大64K字节
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        //收到数据
        datagramSocket.receive(datagramPacket);
        //拆包
        byte[] getData = datagramPacket.getData();
        int offset = datagramPacket.getOffset();
        int length = datagramPacket.getLength();
        String data = new String(getData, offset, length);
        InetAddress address = datagramPacket.getAddress();
        String hostAddress = address.getHostAddress();
        int port = datagramPacket.getPort();
        //----拆包结果
        System.out.println("收到数据，开始拆包....");
        System.out.println("接收到的数据：" + data);
        System.out.println("发送者主机：" + hostAddress);
        System.out.println("发送者端口：" + port);
        //--------
        System.out.println("-----------数据接收完毕");
        System.out.println("开始应答...");
        //byte buf[], int offset, int length, InetAddress address, int port)
        String msg = "No, No, No!";
        DatagramPacket datagramPacket1 = new DatagramPacket(msg.getBytes(StandardCharsets.UTF_8), 0, msg.length(), InetAddress.getLocalHost(), 8888);
        datagramSocket.send(datagramPacket1);
        System.out.println("应答完毕，开始关闭Socket");
        datagramSocket.close();
    }
}

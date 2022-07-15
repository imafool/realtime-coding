package com.coco.net._homework.caseB;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * 接收端在6666端口接收数据，收到问题回复：四大名著是《三国》《水浒》《推油》《郭德纲相声选》，否则返回：我不懂你的意思
 */
public class Reciever {
    public static void main(String[] args) throws IOException {
        System.out.println("--------Receiver--------");
        //接收数据
        DatagramSocket datagramSocket = new DatagramSocket(6666);
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        datagramSocket.receive(datagramPacket);
        byte[] data = datagramPacket.getData();
        int offset = datagramPacket.getOffset();
        int length = datagramPacket.getLength();
        String s = new String(data, offset, length);
        System.out.println("receive Q: " + s);

        //返回应答
        String receiMsg;
        if (s.equals("What are the new four great Classics?")){
            receiMsg = "The four famous novels are The Three Kingdoms, Water Margin, Oil Push and Selected Crosstalk by Guo Degang.";
        }else{
            receiMsg = "I don't know what you mean!";
        }
        DatagramPacket datagramPacket1 = new DatagramPacket(receiMsg.getBytes(StandardCharsets.US_ASCII), 0, receiMsg.length(), InetAddress.getLocalHost(), 7777);
        datagramSocket.send(datagramPacket1);

        datagramSocket.close();
    }
}

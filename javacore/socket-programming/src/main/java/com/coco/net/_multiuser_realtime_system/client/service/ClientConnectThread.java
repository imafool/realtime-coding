package com.coco.net._multiuser_realtime_system.client.service;

import com.coco.net._multiuser_realtime_system.common.Message;
import com.coco.net._multiuser_realtime_system.common.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.*;
import java.net.Socket;

/**
 * 持有Socket的线程
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class ClientConnectThread extends Thread {
    private Socket socket;

    @Override
    public void run() {
        //因为该线程持有socket需要与服务端保持通信
        while (true){
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();//如果服务端没有数据，线程阻塞，等待，获取消息处理
                String type = message.getMsgType();
                String sysInfo;

                if (type.equals(MessageType.MESSAGE_RET_ONLINE_USERS)){//处理在线用户列表
                    String[] onlineUsers = message.getContent().split(" ");
                    sysInfo = "==========当前在线用户列表=========";
                    System.out.println(sysInfo);
                    for (String userName : onlineUsers){
                        System.out.println("用户：" + userName);
                    }
                }else if (type.equals(MessageType.MESSAGE_CUSTOM_MESSAGE)){//聊天
                    sysInfo = "\n" + message.getSender() + " 说：" + message.getContent() + "\n";
                    System.out.println(sysInfo);
                }else if (type.equals(MessageType.MESSAGE_CUSTOM_MESSAGE_TO_ALL)){//群发
                    sysInfo = "\n" + message.getSender() + "对大家说：" + message.getContent() + "\n";
                    System.out.println(sysInfo);
                }else if (type.equals(MessageType.MESSAGE_FILE)){//文件传输
                    String filepath = message.getDestPath();
                    System.out.println("已经在路径：" + filepath + "下接收了文件");
                    byte[] file = message.getFile();
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filepath));
                    bos.write(file, 0, file.length);
                    bos.flush();
                    bos.close();
                }else{
                    //TODO 扩展更多类型
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

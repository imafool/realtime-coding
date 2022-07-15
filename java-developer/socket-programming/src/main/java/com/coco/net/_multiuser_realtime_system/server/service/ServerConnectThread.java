package com.coco.net._multiuser_realtime_system.server.service;

import com.coco.net._multiuser_realtime_system.common.Message;
import com.coco.net._multiuser_realtime_system.common.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 一个对象与一个客户端连接保持通信
 */
@AllArgsConstructor @NoArgsConstructor @Getter
public class ServerConnectThread extends Thread {
    private Socket socket;
    private String userId; //与之连接的userId

    @Override
    public void run() {
        while(true){
            System.out.println("服务端和客户端"+ userId +"保持通信中...");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //消息处理
                if (message.getMsgType().equals(MessageType.MESSAGE_GET_ONLINE_USERS)){
                    //当前线程关联的Socket回复消息
                    String onlineUserListString = ServerConnectThreadManager.returnOnlineUsers();
                    Message message1 = new Message();
                    message1.setMsgType(MessageType.MESSAGE_RET_ONLINE_USERS);
                    message1.setContent(onlineUserListString);
                    message1.setGetter(message.getSender());

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message1);
                }else if(message.getMsgType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    String sender = message.getSender();
                    System.out.println(sender + "退出登录");
                    //将与之关联的线程移除
                    ServerConnectThreadManager.remove(sender);
                    //关闭socket
                    socket.close();
                    //结束当前线程
                    break;
                }else if(message.getMsgType().equals(MessageType.MESSAGE_CUSTOM_MESSAGE)) {
                    ServerConnectThread serverConnectThread = ServerConnectThreadManager.get(message.getGetter());
                    if (serverConnectThread != null){
                        ObjectOutputStream oos = new ObjectOutputStream(serverConnectThread.getSocket().getOutputStream());
                        oos.writeObject(message);
                    }else{
                        //如果对方不在线，将消息保存到数据库
                        System.out.println("接收方不在线，消息已保存到数据库！");
                    }
                }else if(message.getMsgType().equals(MessageType.MESSAGE_CUSTOM_MESSAGE_TO_ALL)) {
                    HashMap<String, ServerConnectThread> allThreadHashMap = ServerConnectThreadManager.getAllThread();
                    allThreadHashMap.remove(userId);
                    Iterator<String> iterator = allThreadHashMap.keySet().iterator();
                    while (iterator.hasNext()){
                        String userId = iterator.next();
                        ObjectOutputStream oos = new ObjectOutputStream(allThreadHashMap.get(userId).getSocket().getOutputStream());
                        oos.writeObject(message);
                    }
                }else if(message.getMsgType().equals(MessageType.MESSAGE_FILE)) {
                    ServerConnectThread serverConnectThread = ServerConnectThreadManager.get(message.getGetter());
                    Socket socket = serverConnectThread.getSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                    System.out.println("服务端：文件已传输给对方！");
                }else{
                    System.out.println("待扩展..."); //TODO 待扩展...
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}

package com.coco.net._multiuser_realtime_system.server.service;

import com.coco.net._multiuser_realtime_system.common.Message;
import com.coco.net._multiuser_realtime_system.common.MessageType;
import com.coco.net._multiuser_realtime_system.common.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端，监听等待客户端连接
 */
public class ServerService {
    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        new ServerService();
    }

    public ServerService(){
        try {
            System.out.println("服务端等待连接...");
            serverSocket = new ServerSocket(9999);

            new Thread(new NewsPushToThread()).start(); //推送服务线程

            //来一个用户，开启一个线程，添加到线程管理集合
            while(true){
                //获取客户端请求
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                User user = (User) ois.readObject();

                //响应客户端
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                Message message = new Message();

                //验证逻辑
                if (user.getUserId().equals("you") && user.getPasswd().equals("you")
                        || user.getUserId().equals("me") && user.getPasswd().equals("me")
                        || user.getUserId().equals("she") && user.getPasswd().equals("she")
                        || user.getUserId().equals("root") && user.getPasswd().equals("root")){
                    message.setMsgType(MessageType.MESSAGE_LOGIN_SUCCESS);
                    oos.writeObject(message);

                    //通信成功，使用一个线程与此客户端保持连接
                    ServerConnectThread serverConnectThread = new ServerConnectThread(socket, user.getUserId());
                    serverConnectThread.start();
                    //线程管理
                    ServerConnectThreadManager.add(user.getUserId(), serverConnectThread);
                }else{
                    //登录失败
                    message.setMsgType(MessageType.MESSAGE_LOGIN_FAILURE);
                    oos.writeObject(message);
                    socket.close();
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (serverSocket != null){
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

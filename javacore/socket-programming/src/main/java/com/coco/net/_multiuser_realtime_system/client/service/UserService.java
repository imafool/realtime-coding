package com.coco.net._multiuser_realtime_system.client.service;

import com.coco.net._multiuser_realtime_system.common.Message;
import com.coco.net._multiuser_realtime_system.common.MessageType;
import com.coco.net._multiuser_realtime_system.common.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 用户服务：登录认证，请求获取在线用户列表
 */
public class UserService {

    private User user = new User();

    private Socket socket;

    //登录认证
    public boolean checkUser(String userId, String passwd){
        user.setUserId(userId);
        user.setPasswd(passwd);

        boolean flag = false;

        try {
            socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 9999);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            //向服务端发送User对象
            oos.writeObject(user);

            //读取服务端回复的Message对象
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Message message = (Message) ois.readObject();

            if (message.getMsgType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)){
                //启动一个线程，持有Socket，保持与服务端通信
                ClientConnectThread connectThread = new ClientConnectThread(socket);
                connectThread.start();
                //[ 由于多客户端，所以需要多个线程，需要用一个集合对多个线程进行管理 ]
                ClientConnectThreadManager.add(userId, connectThread);

                flag = true;

            }else{
                //线程启动失败，只需要关闭socket
                socket.close();

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return flag;
    }

    //请求获取在线用户列表
    public void onlineUserList(){
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_GET_ONLINE_USERS);
        message.setSender(user.getUserId());

        //获取当前线程的Socket关联的输出流
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ClientConnectThreadManager.get(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //客户端退出
    public void logout(){
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserId());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ClientConnectThreadManager.get(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(user.getUserId() + "登出");
            System.exit(0);//退出进程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

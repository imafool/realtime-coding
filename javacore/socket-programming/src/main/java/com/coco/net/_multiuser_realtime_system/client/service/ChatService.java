package com.coco.net._multiuser_realtime_system.client.service;

import com.coco.net._multiuser_realtime_system.common.Message;
import com.coco.net._multiuser_realtime_system.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 聊天服务
 */
public class ChatService {
    //发送消息
    public void sendToSomebody(String sender, String receiver, String content){
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_CUSTOM_MESSAGE);
        message.setSender(sender);
        message.setGetter(receiver);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println(sender + "对" + receiver + "说了：" + content);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ClientConnectThreadManager.get(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAll(String sender, String content) {
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_CUSTOM_MESSAGE_TO_ALL);
        message.setSender(sender);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println(sender + "对所有人说了：" + content);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ClientConnectThreadManager.get(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

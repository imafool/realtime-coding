package com.coco.net._multiuser_realtime_system.server.service;

import com.coco.net._multiuser_realtime_system.client.utils.InputUtils;
import com.coco.net._multiuser_realtime_system.common.Message;
import com.coco.net._multiuser_realtime_system.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 新闻推送
 */
public class NewsPushToThread implements Runnable{
    @Override
    public void run() {
        while(true){
            System.out.println("请输入Server要推送的新闻消息[键入bye/exit/quit结束推送服务]：");
            String news = InputUtils.readString(100);
            if (news.equals("bye") || news.equals("exit") || news.equals("quit")){
                break; //结束线程
            }
            Message message = new Message();
            message.setSender("服务器");
            message.setContent(news);
            message.setSendTime(new Date().toString());
            message.setMsgType(MessageType.MESSAGE_NEWS);
            System.out.println("推送消息内容【" + news + "】给所有人");

            HashMap<String, ServerConnectThread> allThread = ServerConnectThreadManager.getAllThread();
            Iterator<String> iterator = allThread.keySet().iterator();
            while (iterator.hasNext()){
                String onlineUser = iterator.next();
                try{
                    ObjectOutputStream oos = new ObjectOutputStream(allThread.get(onlineUser).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

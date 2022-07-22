package com.coco.net._multiuser_realtime_system.client.service;

import com.coco.net._multiuser_realtime_system.common.Message;
import com.coco.net._multiuser_realtime_system.common.MessageType;

import java.io.*;
import java.util.Date;

/**
 * 文件传输
 */
public class FileService {

    //发送文件消息
    public void sendFile(String userId, String getter, String sourPath, String destPath) {
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_FILE);
        message.setSender(userId);
        message.setGetter(getter);
        long size = new File(sourPath).length();
        message.setSendTime(new Date().toString());
        System.out.println(userId + "给" + getter + "发了：" + size + "大小的文件");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ClientConnectThreadManager.get(userId).getSocket().getOutputStream());
            BufferedInputStream localInput = new BufferedInputStream(new FileInputStream(sourPath));
            byte[] bytes = new byte[8000000];
            int readLen;
            while ((readLen = localInput.read(bytes)) != -1) {
                String s = new String(bytes, 0, readLen);
                message.setFile(s.getBytes());
            }
            message.setDestPath(destPath);
            oos.writeObject(message);
            System.out.println("文件发送完毕！");
            localInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
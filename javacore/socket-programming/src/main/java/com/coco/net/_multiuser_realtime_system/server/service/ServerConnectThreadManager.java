package com.coco.net._multiuser_realtime_system.server.service;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 线程管理
 */
public class ServerConnectThreadManager {
    private static HashMap<String, ServerConnectThread> hashMap = new HashMap<>();

    public static void add(String userId, ServerConnectThread serverConnectThread){
        hashMap.put(userId, serverConnectThread);
    }

    public static ServerConnectThread get(String userId){
        return hashMap.get(userId);
    }

    //返回当前线程集合中线程
    public static String returnOnlineUsers() {
        Iterator<String> iterator = hashMap.keySet().iterator();
        StringBuilder onlineUserList = new StringBuilder();
        while (iterator.hasNext()){
            onlineUserList.append(iterator.next()).append(" ");
        }
        return onlineUserList.toString();
    }

    //线程集合中移除指定线程
    public static void remove(String sender) {
        hashMap.remove(sender);
    }

    public static HashMap<String, ServerConnectThread> getAllThread() {
        return hashMap;
    }
}

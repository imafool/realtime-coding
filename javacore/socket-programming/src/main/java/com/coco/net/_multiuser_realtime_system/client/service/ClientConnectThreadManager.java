package com.coco.net._multiuser_realtime_system.client.service;

import java.util.HashMap;

/**
 * 管理客户端连接线程 ClientConnectThread
 */
public class ClientConnectThreadManager {
    //存放线程的集合
    private static HashMap<String, ClientConnectThread> hashMap = new HashMap<>();

    public static void add(String userId, ClientConnectThread clientConnectThread){
        hashMap.put(userId, clientConnectThread);
    }

    public static ClientConnectThread get(String userId){
        return hashMap.get(userId);
    }
}

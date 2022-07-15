package com.coco;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * 创建Zookeeper客户端，连接Zookeeper集群
 * 前置：（1）3+ Zookeeper Servers，并启动成功；（2）修改hosts文件，使用服务名代替IP访问；
 */
public class ZookeeperClient {
    private static String cluster = "ubuntu-1:2181,ubuntu-2:2181,ubuntu-3:2181";

    private static int sessionTimeout = 2000;

    public static void main(String[] args) throws IOException {
        initZk();
    }

    private static void initZk() throws IOException {
        //Constructor : ZooKeeper(String connectString, int sessionTimeout, Watcher watcher)
        ZooKeeper zkClient = new ZooKeeper(cluster, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                //TODO
            }
        });
    }


}

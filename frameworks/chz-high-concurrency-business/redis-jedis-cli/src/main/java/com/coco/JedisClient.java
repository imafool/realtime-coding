package com.coco;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisClient {
    public static void main(String[] args) {
        //vi /etc/redis/redis.conf
        //bind
        //protected-mode
        String host = "192.168.56.102";
        int port = 6379;
        String passwd = "123456";

        //get Jedis Client
        Jedis jedis = new Jedis(host, port);
        jedis.auth(passwd);
        String pong = jedis.ping();
        System.out.println(pong);

        //select database
        jedis.select(0);

        //ops [string, list, set, hash, zset, hyperloglog, bigmaps, geospatial]
        Set<String> keys = jedis.keys("*");
        for (String key : keys){
            System.out.println(key);
        }
    }
}

package com.coco.service;

/**
 * 持久化Redis中的数据
 */
public interface RedisRedPacketService {
    void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount);
}

package com.coco.service;

public interface UserRedPacketService {
	//正常情况
	int grapRedPacket(Long redPacketId, Long userId);
	//悲观锁
	int grapRedPacketForUpdate(Long redPacketId, Long userId);
	//乐观锁+版本号
	int grapRedPacketForVersion(Long redPacketId, Long userId);
	//乐观锁+版本号+按时间戳重入
	int grapRedPacketForVersionByTimestamp(Long redPacketId, Long userId);
	//乐观锁+版本号+按重试次数重入
	int grapRedPacketForVersionByRetries(Long redPacketId, Long userId);
	//Redis实现
	long grapRedPacketForRedis(Long redPacketId, Long userId);
}

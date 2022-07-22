package com.coco.dao;

import com.coco.pojo.UserRedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedPacketDao {

	/**
	 * 插入用户抢红包信息
	 */
	int grapRedPacket(UserRedPacket userRedPacket);
}

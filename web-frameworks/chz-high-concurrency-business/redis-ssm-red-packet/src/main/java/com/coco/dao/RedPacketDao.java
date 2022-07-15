package com.coco.dao;

import com.coco.pojo.RedPacket;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RedPacketDao {

	/**
	 * 获取红包信息
	 */
	RedPacket getRedPacket(Long id);

	/**
	 * 获取红包信息 悲观锁
	 */
	RedPacket getRedPacketForUpdate(Long id);



	/**
	 * 扣减红包
	 */
	int decreaseRedPacket(Long id);

	/**
	 * 扣减红包 乐观锁
	 * mybatis exception :(
	 */
	int decreaseRedPacketForVersion(@Param("id") Long id, @Param("version") Integer version);
}
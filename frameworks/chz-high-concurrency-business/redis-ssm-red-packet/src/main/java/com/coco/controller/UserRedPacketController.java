package com.coco.controller;

import com.coco.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/userRedPacket")
public class UserRedPacketController {

	@Autowired
	private UserRedPacketService userRedPacketService = null;

	/**
	 * result: 浏览器模拟30000次请求，数据库操作共花费30min，查看结果，发现有20005条记录，出现书中所说的超发现象
	 * 两个问题：
	 * 1.逻辑错误：硬性
	 * 2.性能
	 */
	@RequestMapping(value = "/grapRedPacket")
	@ResponseBody
	public String grapRedPacket(Long redPacketId, Long userId) {
		return userRedPacketService.grapRedPacket(redPacketId, userId) > 0 ? "yes" : "not";
	}

	/**
	 * 使用悲观锁的方式：
	 * 1.逻辑问题被解决；
	 * 2.使用悲观锁，在高并发场景中，大量线程抢占资源，事务锁定释放，造成大量线程被挂起和恢复（大量线程阻塞被挂起）
	 */
	@RequestMapping(value = "/grapRedPacketForUpdate")
	@ResponseBody
	public String grapRedPacketForUpdate(Long redPacketId, Long userId) {
		return userRedPacketService.grapRedPacketForUpdate(redPacketId, userId) > 0 ? "yes" : "not";
	}

	/**
	 * 使用乐观锁的方式：
	 * 1.不用数据库的锁实现，不阻塞其他线程，不会引发线程频繁挂起和恢复，从而提高并发能力
	 * 2.乐观锁使用CAS原理：对于多线程公用的资源，先保存一个旧值，经过一定逻辑处理，在最后扣减时先比较当前值是否与旧值一致，一致再修改，不一致就认为已经被其他线程操作过，放弃操作
	 * 		：有时候可以重试，是个可重入锁！
	 * 3.ABA问题：线程1和线程2描述
	 * 	解决：在一个数据中加入版本号，对于这个版本号只能递增，不能回退，就解决ABA问题了。（这里的version字段只作为记录更新次数用）
	 */
	@RequestMapping(value = "/grapRedPacketForVersion")
	@ResponseBody
	public String grapRedPacketForVersion(Long redPacketId, Long userId) {
		return userRedPacketService.grapRedPacketForVersion(redPacketId, userId) > 0 ? "yes" : "not";
	}

	/**
	 * 乐观锁：只发送了不到28740个请求，成功执行SQL的只有5001条
	 * 问题：由于版本并发问题较高概率发生，存在很多失败的现象
	 * 解决：根据可重入加入两种限制：（1）按照时间戳限制；（2）按照重试次数限制
	 */
	@RequestMapping(value = "/grapRedPacketForVersionByTimestamp")
	@ResponseBody
	public String grapRedPacketForVersionByTimestamp(Long redPacketId, Long userId) {
		return userRedPacketService.grapRedPacketForVersionByTimestamp(redPacketId, userId) > 0 ? "yes" : "not";
	}
	@RequestMapping(value = "/grapRedPacketForVersionByRetries")
	@ResponseBody
	public String grapRedPacketForVersionByRetries(Long redPacketId, Long userId) {
		return userRedPacketService.grapRedPacketForVersionByRetries(redPacketId, userId) > 0 ? "yes" : "not";
	}

	/**
	 * 使用Redis实现：
	 * 1.Redis使用内存，比磁盘速度快的多
	 * 2.虽然功能没有数据库强大，事务也不完整，但是Lua语言是原子性的(可以保证数据的一致性)
	 * 3.Redis中存储数据不是长久之计，，最多是为了提供更加快速的缓存，达到一定的条件，会将数据持久化到数据库
	 * result:
	 * 共花费1min9s，bingo！
	 */
	@RequestMapping(value = "/grapRedPacketForRedis")
	@ResponseBody
	public String grapRedPacketForRedis(Long redPacketId, Long userId) {
		return userRedPacketService.grapRedPacketForRedis(redPacketId, userId) > 0 ? "yes" : "not";
	}

}

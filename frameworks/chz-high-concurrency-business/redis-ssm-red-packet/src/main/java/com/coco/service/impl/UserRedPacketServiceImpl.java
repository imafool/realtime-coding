package com.coco.service.impl;

import com.coco.dao.RedPacketDao;
import com.coco.dao.UserRedPacketDao;
import com.coco.pojo.RedPacket;
import com.coco.pojo.UserRedPacket;
import com.coco.service.RedisRedPacketService;
import com.coco.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

	@Autowired
	private UserRedPacketDao userRedPacketDao = null;

	@Autowired
	private RedPacketDao redPacketDao = null;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate = null;

	@Autowired
	private RedisRedPacketService redisRedPacketService = null;

	private static final int FAILED = 0;

	//Lua Script
	String luaScript = "local listKey = 'red_packet_list_'..KEYS[1]\n" +
			"local redPacket = 'red_packet_'..KEYS[1]\n" +
			"local stock = tonumber(redis.call('hget', redPacket, 'stock'))\n" +
			"if stock <= 0 then return 0 end\n" +
			"stock = stock - 1\n" +
			"redis.call('hset', redPacket, 'stock', tostring(stock))\n" +
			"redis.call('rpush', listKey, ARGV[1])\n" +
			"if stock == 0 then return 2 end\n" +
			"return 1";

	String sha1 = null;

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacket(Long redPacketId, Long userId) {
		RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
		if (redPacket.getStock() > 0) {
			redPacketDao.decreaseRedPacket(redPacketId);
			UserRedPacket userRedPacket = new UserRedPacket();
			userRedPacket.setRedPacketId(redPacketId);
			userRedPacket.setUserId(userId);
			userRedPacket.setAmount(redPacket.getUnitAmount());
			userRedPacket.setNote("抢红包 " + redPacketId);
			return userRedPacketDao.grapRedPacket(userRedPacket);
		}
		return FAILED;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacketForUpdate(Long redPacketId, Long userId) {

		//悲观锁
		RedPacket redPacket = redPacketDao.getRedPacketForUpdate(redPacketId);

		if (redPacket.getStock() > 0) {
			redPacketDao.decreaseRedPacket(redPacketId);
			UserRedPacket userRedPacket = new UserRedPacket();
			userRedPacket.setRedPacketId(redPacketId);
			userRedPacket.setUserId(userId);
			userRedPacket.setAmount(redPacket.getUnitAmount());
			userRedPacket.setNote("抢红包 " + redPacketId);
			return userRedPacketDao.grapRedPacket(userRedPacket);
		}
		return FAILED;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacketForVersion(Long redPacketId, Long userId) {
		RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
		if (redPacket.getStock() > 0) {

			//乐观锁
			int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
			if (update == 0){
				return FAILED;
			}

			UserRedPacket userRedPacket = new UserRedPacket();
			userRedPacket.setRedPacketId(redPacketId);
			userRedPacket.setUserId(userId);
			userRedPacket.setAmount(redPacket.getUnitAmount());
			userRedPacket.setNote("抢红包 " + redPacketId);
			return userRedPacketDao.grapRedPacket(userRedPacket);
		}
		return FAILED;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacketForVersionByRetries(Long redPacketId, Long userId) {

		long start = System.currentTimeMillis();
		while(true){
			long end = System.currentTimeMillis();
			//限制100ms内可重入
			if (end - start > 100){
				return FAILED;
			}

			RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
			if (redPacket.getStock() > 0) {
				int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
				if (update == 0){
					//保持无线循环
					continue;
				}
				UserRedPacket userRedPacket = new UserRedPacket();
				userRedPacket.setRedPacketId(redPacketId);
				userRedPacket.setUserId(userId);
				userRedPacket.setAmount(redPacket.getUnitAmount());
				userRedPacket.setNote("抢红包 " + redPacketId);
				return userRedPacketDao.grapRedPacket(userRedPacket);
			}else{
				return FAILED;
			}

		}
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacketForVersionByTimestamp(Long redPacketId, Long userId) {
		//限制重试次数
		for (int i = 0; i < 3; i++) {
			RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
			if (redPacket.getStock() > 0) {
				int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
				if (update == 0){
					continue;
				}
				UserRedPacket userRedPacket = new UserRedPacket();
				userRedPacket.setRedPacketId(redPacketId);
				userRedPacket.setUserId(userId);
				userRedPacket.setAmount(redPacket.getUnitAmount());
				userRedPacket.setNote("抢红包 " + redPacketId);
				return userRedPacketDao.grapRedPacket(userRedPacket);
			}else{
				return FAILED;
			}
		}
		return FAILED;
	}

	//使用redis结合lua脚本，完成抢红包操作，在完成之后批量持久化到数据库
	//lua执行保证数据的一致性，逻辑是正确的，基于内存的操作，提高系统性能，完美实现目标
	@Override
	public long grapRedPacketForRedis(Long redPacketId, Long userId) {
		String args = userId + "-" + System.currentTimeMillis();
		Long result;
		Jedis jedis = (Jedis)redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
		try{
			if (sha1 == null){
				sha1 = jedis.scriptLoad(luaScript);
			}
			Object res = jedis.evalsha(sha1, 1, redPacketId + "", args);
			result = (Long) res;
			if (result == 2){
				String unitAmountStr = jedis.hget("red_packet_" + redPacketId, "unit_amount");
				//触发持久化
				Double unitAmount = Double.parseDouble(unitAmountStr);
				System.err.println("当前线程名： " + Thread.currentThread().getName());
				redisRedPacketService.saveUserRedPacketByRedis(redPacketId, unitAmount);
			}
		}finally {
			if (jedis != null && jedis.isConnected()){
				jedis.close();
			}
		}
		return result;
	}
}

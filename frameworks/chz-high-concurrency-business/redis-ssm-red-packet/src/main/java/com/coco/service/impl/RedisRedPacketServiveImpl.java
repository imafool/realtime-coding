package com.coco.service.impl;

import com.coco.pojo.UserRedPacket;
import com.coco.service.RedisRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisRedPacketServiveImpl implements RedisRedPacketService {
    private static final String PRIFIX = "red_packet_list_";

    //每次取出1000条数据，避免JVM消耗过多内存影响性能
    private static final int TIME_SIZE = 1000;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate = null;

    @Autowired
    private DataSource dataSource = null;

    //从redis中持久化数据到数据库
    @Override
    @Async //开启新线程运行:让Spring自动创建另外一条线程去运行
    public void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount) {
        System.out.println("开始保存数据");
        long start = System.currentTimeMillis();
        BoundListOperations<?, ?> ops = redisTemplate.boundListOps(PRIFIX + redPacketId);
        Long size = ops.size();
        long times = size % TIME_SIZE == 0 ? size / TIME_SIZE : size / TIME_SIZE + 1;
        int count = 0;
        List<UserRedPacket> userRedPacketList = new ArrayList<>(TIME_SIZE);
        for (int i = 0; i < times; i++) {
            List userIdList;
            if (i == 0){
                userIdList = ops.range(i * TIME_SIZE, (i + 1) * TIME_SIZE);
            }else{
                userIdList = ops.range(i * TIME_SIZE + 1, (i + 1) * TIME_SIZE);
            }
            userRedPacketList.clear();
            for (int j = 0; j < userIdList.size(); j++) {
                String args = userIdList.get(j).toString();
                String[] arr = args.split("-");
                String userIdStr = arr[0];
                String timeStr = arr[1];
                long userId = Long.parseLong(userIdStr);
                long time = Long.parseLong(timeStr);
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(unitAmount);
                userRedPacket.setGrabTime(new Timestamp(time));
                userRedPacket.setNote("redis...");
                userRedPacketList.add(userRedPacket);
            }
            count += executeBatch(userRedPacketList);
        }
        redisTemplate.delete(PRIFIX + redPacketId);
        long end = System.currentTimeMillis();
        System.out.println("保存"+count+"条数据，耗时"+(end - start)+"毫秒");
    }

    //数据库批处理操作
    private int executeBatch(List<UserRedPacket> userRedPacketList) {
        Connection connection = null;
        Statement statement;
        int []count;
        try{
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (UserRedPacket packet : userRedPacketList){
                String sql1 = "update T_RED_PACKET set stock = stock - 1 where id = " + packet.getRedPacketId();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sql2 = "insert into T_USER_RED_PACKET(red_packet_id, user_id, amount, grab_time, note) " +
                        "values " +
                        "("
                        + packet.getRedPacketId() + ", "
                        + packet.getUserId() + ", "
                        + packet.getAmount() + ", "
                        + "'" + dateFormat.format(packet.getGrabTime()) + "', "
                        + "'" + packet.getNote() + "'"
                        + ")";
                statement.addBatch(sql1);
                statement.addBatch(sql2);
            }
            //批量提交
            count = statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("批量持久化错误");
        }finally {
            try{
                if (connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count.length / 2;
    }
}

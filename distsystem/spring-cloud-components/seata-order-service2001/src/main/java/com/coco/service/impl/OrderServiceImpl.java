package com.coco.service.impl;

import com.coco.dao.OrderDao;
import com.coco.domain.Order;
import com.coco.feign.AccountService;
import com.coco.feign.StorageService;
import com.coco.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;


    @Override
    //控制全局事务
    @GlobalTransactional(name = "txl-create-order", rollbackFor = Exception.class)
    //TC：seata的服务器，事务协调器
    //TM：事务管理器，事务发起者
    //        @GlobalTransactional(name = "txl-create-order", rollbackFor = Exception.class)
    //RM：事务的参与者，就是一个数据库，作为全局事务的分支（就是完成一个业务所涉及到的关键数据部分）

    //事务流程：
    // 1.  TM开启分布式事务（TM向TC注册全局事无记录）；@GlobalTransactional(name = "txl-create-order", rollbackFor = Exception.class)
    // 2.  按业务场景，编排数据库，服务等事务内资源（TM向TC汇报资源准备状态）；资源管理器要向事务协调器汇报状态。
    // 3.  TM结束分布式事务，事务一阶段结束（TM通知TC提交/回滚分布式事务）；TM通知TC是提交/回滚
    // 4.  TC汇总事务信息，决定分布式事务是提交还是回滚。
    // 5.  TC通知所有RM提交/回滚资源，事务二阶段结束。

    //解释：
    //一阶段：业务数据和回滚日志记录在同一个本地事务中提交，释放本地锁和连接资源。
    //二阶段：
    //     提交异步化，非常快速地完成。
    //     回滚通过一阶段的回滚日志进行反向补偿。
    public void create(Order order) {
        log.info("--------------开始新建订单");
        orderDao.create(order);
        log.info("--------------订单服务调用库存服务，做库存扣减");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("--------------库存扣减结束");
        log.info("--------------订单服务调用账户服务，做账户扣减");
        accountService.decrease(order.getProductId(), order.getMoney());
        log.info("--------------账户扣减结束");
        log.info("--------------修改订单状态");
        orderDao.update(order.getUserId(), 0);
        log.info("--------------订单状态修改为：已完成");

        log.info("--------------下单完成！");
    }

    @Override
    public void update(Long userId, Integer status) {

    }
}

package com.coco.lb_algo;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//TODO 2022-3-3 16:21:26 有问题
@Component
public class LoadBalanceAlgoImpl implements LoadBalance {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncr(){
        int curr;
        int next;
        do{
            curr = this.atomicInteger.get();
            next = curr >= Integer.MAX_VALUE ? 0 : curr + 1;
        }while (!this.atomicInteger.compareAndSet(curr, next));
        System.out.println("访问次数" + next);
        return next;
    }

    //算法实现
    @Override
    public ServiceInstance instance(List<ServiceInstance> instances) {
        int index = getAndIncr() % instances.size();
        return instances.get(index);
    }
}

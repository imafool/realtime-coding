package com.coco.lb_algo;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

//自定义负载均衡算法：获取注册中心服务实例列表，通过自定义负载均衡算法选出一个服务实例提供服务
public interface LoadBalance {
    ServiceInstance instance(List<ServiceInstance> instances);
}

package com.coco.controller;

import com.coco.entites.CommonResult;
import com.coco.entites.Payment;
import com.coco.lb_algo.LoadBalance;
import com.coco.lb_algo.LoadBalanceAlgoImpl;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private EurekaDiscoveryClient discoveryClient;

    // private static final String PAYMENT_URL = "http://localhost:8001";

    //修改为通过服务名访问（未知主机异常，Eureka通过服务名称访问，需要在RestTemplate开启均衡，从而可以根据一定的LB算法访问某个主机）
    private static String service = "CLOUD-PAYMENT-SERVICE";

    private static String http = "http://";

    private static final String PAYMENT_URL = http + service;

    //通过RestTemplate实现跨服务调用
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create", payment, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id, CommonResult.class);
    }

    /**
     * 使用自定义负载均衡算法
     */
    @GetMapping(value = "/consumer/lb")
    public String getPaymentById(){
        //从注册中心获取所有服务实例
        List<ServiceInstance> instances = discoveryClient.getInstances(service);
        //选出一个实例
        LoadBalance lb = new LoadBalanceAlgoImpl();
        ServiceInstance instance = lb.instance(instances);
        //服务
        return restTemplate.getForObject(http + instance.getHost() + ":" + instance.getPort() +"/payment/lb", String.class);
    }
}

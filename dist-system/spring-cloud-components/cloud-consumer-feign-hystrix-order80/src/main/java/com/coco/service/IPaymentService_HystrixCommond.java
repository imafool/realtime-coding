package com.coco.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

//在服务提供者侧提供降级
@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT-SERVICE")
public interface IPaymentService_HystrixCommond {
    @GetMapping("/pay/hystrixcommond")
    String hystrixcommond();
}

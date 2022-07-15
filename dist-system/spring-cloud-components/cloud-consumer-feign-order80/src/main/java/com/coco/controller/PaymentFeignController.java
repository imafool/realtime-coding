package com.coco.controller;

import com.coco.entites.CommonResult;
import com.coco.entites.Payment;
import com.coco.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PaymentFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/timeout")
    public CommonResult<Payment> getPaymentById(){
        return paymentFeignService.timeout();
    }

    @GetMapping(value = "/comsumer/gw")
    public String testGateway(){
        return paymentFeignService.testGateway();
    }

}

package com.coco.controller;

import com.coco.service.IPaymentService;
import com.coco.service.IPaymentService_HystrixCommond;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private IPaymentService paymentService;

    @Resource
    private IPaymentService_HystrixCommond paymentService_hystrixCommond;

    @GetMapping("/order/ok")
    public String okReturn(){
        return paymentService.okReturn();
    }

    @GetMapping("/order/timeout")
    public String timeoutOrError(){
        return paymentService.timeoutOrError();
    }

    @GetMapping("/order/circuit")
    public String circuit(){
        return paymentService.circuit();
    }

    @GetMapping("/order/hystrixcommond")
    public String hystrixcommond(){
        return paymentService_hystrixCommond.hystrixcommond();
    }
}

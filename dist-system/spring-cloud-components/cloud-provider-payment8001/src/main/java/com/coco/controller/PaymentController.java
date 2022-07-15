package com.coco.controller;

import com.coco.entites.CommonResult;
import com.coco.entites.Payment;
import com.coco.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController //REST
@Slf4j //日志
public class PaymentController {

    @Resource //Java自有依赖注入
    private PaymentService paymentService;

    //标识请求的哪个实例
    @Value("${server.port}")
    private Integer port;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("创建结果：" + result);
        if (result > 0){
            return new CommonResult(200, "成功: " + port, result);
        }else{
            return new CommonResult(444, "错误", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("创建结果：" + result);
        if (result != null){
            return new CommonResult(200, "成功: " + port, result);
        }else{
            return new CommonResult(444, "错误", null);
        }
    }

    //LB测试
    @GetMapping(value = "/payment/lb")
    public String lb(){
        return port + "";
    }

    //Openfeign测试
    @GetMapping(value = "/payment/timeout")
    public CommonResult<Payment> timeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    //Gateway测试
    @GetMapping(value = "/gateway")
    public String testGateway(){
        return port.toString();
    }
}

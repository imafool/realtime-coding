package com.coco.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class PaymentController {

    @GetMapping("/pay/ok")
    public String okReturn(){
        return "OK";
    }

    @GetMapping("/pay/timeout")
    public String timeoutOrError(){
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "TIME_OUT";
        // return "ERROR";
    }

    @GetMapping("/pay/circuit")
    public String circuit(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "TIME_OUT";
        // return "ERROR";
    }

    @GetMapping("/pay/hystrixcommond")
    public String hystrixcommond(){
        return getString();
    }

    //降级处理（熔断也在此处配置，属性包括：开启断路器，请求次数，时间窗口，失败率）
    @HystrixCommand(fallbackMethod = "timeoutHandle", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    private String getString() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "TIME_OUT";
        // return "ERROR";
    }

    public String timeoutHandle(){
        return "降级 @HystrixCommand";
    }
}

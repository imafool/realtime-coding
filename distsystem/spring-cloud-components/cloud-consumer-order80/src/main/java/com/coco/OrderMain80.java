package com.coco;

import com.ribbon.lb_policy.irule.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient //标识为Eureka客户端
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MyRule.class) //替换策略
public class OrderMain80 {
    public static void main(String[] args){
        SpringApplication.run(OrderMain80.class, args);
    }
}

package com.ribbon.lb_policy.irule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Ribbon的配置类，不放在@ComponentScan能扫描到的,否则被所有客户端公用
@Configuration
public class MyRule {

    @Bean(name = "randomRule")
    public IRule randomRule(){
        return new RandomRule();
    }
}

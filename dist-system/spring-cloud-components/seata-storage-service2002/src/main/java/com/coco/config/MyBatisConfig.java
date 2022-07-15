package com.coco.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.coco.dao"})
public class MyBatisConfig {
}

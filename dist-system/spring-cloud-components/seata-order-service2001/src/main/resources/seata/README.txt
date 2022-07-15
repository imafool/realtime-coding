1.注意版本兼容：案例中使用：Aliababa 2.2.1， Seata 1.2.0
2.注意不同版本的Seata所需要的依赖：案例中使用Boot-Seata的starter
3.配置文件修改：file.conf, registry.conf
4.应用中设置：spring.cloud.alibaba.seata.tx-service-group=my_test_tx_group（自定义，保持一致）
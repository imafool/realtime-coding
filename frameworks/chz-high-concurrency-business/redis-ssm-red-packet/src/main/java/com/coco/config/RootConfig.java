package com.coco.config;

import java.net.UnknownHostException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Spring IoC配置
 */
@Configuration
@ComponentScan(value= "com.*", includeFilters= {@Filter(type = FilterType.ANNOTATION, value ={Service.class})})
@EnableTransactionManagement
//TransactionManagementConfigurer配置
public class RootConfig implements TransactionManagementConfigurer {
	
	private DataSource dataSource = null;

	/**
	 * DataSource Config
	 */
	@Bean(name = "dataSource")
	public DataSource initDataSource() {
		if (dataSource != null) {
			return dataSource;
		}
		Properties props = new Properties();
		props.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
		props.setProperty("url", "jdbc:mysql://localhost:3306/ssm-redis?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC");
		props.setProperty("username", "root");
		props.setProperty("password", "921112");
       	props.setProperty("maxActive", "200");
		props.setProperty("maxIdle", "20");
		props.setProperty("maxWait", "30000");
		try {
			dataSource = BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	//SqlSessionFactoryBean
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactoryBean initSqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(initDataSource());
		//MyBatis Configuration
		Resource resource = new ClassPathResource("mybatis/mybatis-config.xml");
		sqlSessionFactory.setConfigLocation(resource);
		return sqlSessionFactory;
	}
	
	/***
	 * ͨMyBatis Mapper Config
	 */
	@Bean 
	public MapperScannerConfigurer initMapperScannerConfigurer() {
		MapperScannerConfigurer msc = new MapperScannerConfigurer();
		msc.setBasePackage("com.*");
		msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
		msc.setAnnotationClass(Repository.class);
		return msc;
	}

	/**
	 * Transactional Config
	 */
	@Override
	@Bean(name="annotationDrivenTransactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(initDataSource());
		return transactionManager;
	}

	/**
	 * Redis Config
	 */
	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> initRedisTemplate() throws UnknownHostException {
		//-------------Jedis Pool Config-------------
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//最大空闲数
		poolConfig.setMaxIdle(50);
		//最大连接数
		poolConfig.setMaxTotal(100);
		//最大等待毫秒数
		poolConfig.setMaxWaitMillis(20000);
		//-------------Jedis ConnectFactory Config-------------
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(6379);
		connectionFactory.afterPropertiesSet();//后初始化方法:自行创建，Spring不会调用，需要自己调用
		//-------------自定义Redis序列化器-------------
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setDefaultSerializer(stringRedisSerializer);
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(stringRedisSerializer);
		return redisTemplate;
	}
}
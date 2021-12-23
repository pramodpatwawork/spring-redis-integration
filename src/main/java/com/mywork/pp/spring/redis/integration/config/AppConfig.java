package com.mywork.pp.spring.redis.integration.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@ComponentScan("com.mywork.pp.spring.redis.integration")
@PropertySource("classpath:/redis.properties")
public class AppConfig {

    private @Value("${redis.host}") String redisHost;
    private @Value("${redis.port}") int redisPort;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    RedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHost);
        factory.setPort(redisPort);
        factory.setUsePool(true);
        return factory;
    }

    @Bean
    RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    	
    	RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
    		    .entryTtl(Duration.ofSeconds(60))
    			.disableCachingNullValues()
    			.prefixCacheNameWith("student");
    	
    	RedisCacheManager cm = RedisCacheManager.builder(connectionFactory)
    			.cacheDefaults(config)
    			//.withInitialCacheConfigurations(singletonMap("predefined",  
    				//	RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()))
    			//.transactionAware()
    			.build();
        return cm;
    }
}
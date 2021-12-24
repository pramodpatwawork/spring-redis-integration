package com.mywork.pp.spring.redis.integration.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@ComponentScan("com.mywork.pp.spring.redis.integration")
@PropertySource("classpath:/redis.properties")
public class AppConfig {

    private @Value("${redis.host}") String redisHost;
    private @Value("${redis.port}") int redisPort;
    private static final Map<String, RedisCacheConfiguration> redisConfigs = new HashMap<String, RedisCacheConfiguration>();

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

   /* 
    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
      RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
      .master("themaster")
      .sentinel("127.0.0.1", 26579)
      .sentinel("127.0.0.1", 26580);
      return new JedisConnectionFactory(sentinelConfig);
    }*/
     
    /**
     * Lettuce
     */
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
      RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
      .master("mymaster")
      .sentinel("127.0.0.1", 26379)
      .sentinel("127.0.0.1", 26380)
      .sentinel("127.0.0.1", 26381);
      return new LettuceConnectionFactory(sentinelConfig);
    }

    @Bean
    RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        return redisTemplate;
    }

    @Bean
    CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    	
    	RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
    		    .entryTtl(Duration.ofSeconds(300))
    			.disableCachingNullValues();
    	
    	redisConfigs.put("student", config);
    	
    	RedisCacheManager cm = RedisCacheManager.builder(connectionFactory)
    			.cacheDefaults(config)
    			//.withCacheConfiguration("student",config)
    			.withInitialCacheConfigurations(redisConfigs)
    			//.transactionAware()
    			.build();
        return cm;
    }
}
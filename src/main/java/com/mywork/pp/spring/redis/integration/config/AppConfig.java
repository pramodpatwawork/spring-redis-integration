package com.mywork.pp.spring.redis.integration.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
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
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;

@Configuration
@EnableCaching
@ComponentScan("com.mywork.pp.spring.redis.integration")
@PropertySource("classpath:/redis.properties")
public class AppConfig {

    private @Value("${redis.host}") String redisHost;
    private @Value("${redis.port}") int redisPort;
    private @Value("${redis.nodes}") String redisNodes;
    private @Value("${redis.maxRedirects}") int maxRedirects;
    private static final Map<String, RedisCacheConfiguration> redisConfigs = new HashMap<String, RedisCacheConfiguration>();

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean
    public RedisClusterConfiguration  redisClusterConfiguration() {
    	
    	 RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();

         List<RedisNode> nodeList = new ArrayList<>();
         
         String[] nodePairs = redisNodes.split(",");
         
         for(String nodePair: nodePairs) {
        	 String[] nodeHostPort = nodePair.split(":");
        	 nodeList.add(new RedisNode(nodeHostPort[0], Integer.valueOf(nodeHostPort[1])));
         }
         
         clusterConfig.setClusterNodes(nodeList);
         clusterConfig.setMaxRedirects(maxRedirects);
         //config.setPassword(RedisPassword.of(password));
         return clusterConfig;
         
    }

    @Bean
    public ClientOptions clientOptions(){
        return ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .autoReconnect(true)
                .build();
    }
    
    @Bean
    LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options, ClientResources dcr){
    	GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
    	genericObjectPoolConfig.setMaxIdle(50);
    	genericObjectPoolConfig.setMaxTotal(100);    	
    	genericObjectPoolConfig.setMinIdle(10);
    	
    	
    	return LettucePoolingClientConfiguration.builder()
                .poolConfig(genericObjectPoolConfig)
                .clientOptions(options)
                .clientResources(dcr)
                .build();
    }    
    
    /**
     * Lettuce
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(LettucePoolingClientConfiguration lettucePoolingClientConfiguration, 
    					RedisClusterConfiguration  redisClusterConfiguration) {
          	
      return new LettuceConnectionFactory(redisClusterConfiguration, lettucePoolingClientConfiguration);
    }

    @Bean
    RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
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
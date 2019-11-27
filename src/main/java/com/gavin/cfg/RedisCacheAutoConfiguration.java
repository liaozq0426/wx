package com.gavin.cfg;


import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisCacheAutoConfiguration {
    
	/**
	 * @title 基于lettuce的redisTemplate，保证线程安全
	 * @author gavin
	 * @date 2019年5月22日
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean("redisTemplate")
    public RedisTemplate<Object,Object> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> template = new RedisTemplate<Object,Object>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
	
	
	/**
	 * @title stringRedisTemplate
	 * @author gavin
	 * @date 2019年5月22日
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean("stringRedisTemplate")
	public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setConnectionFactory(redisConnectionFactory);
		return template;
		
	}
	
}

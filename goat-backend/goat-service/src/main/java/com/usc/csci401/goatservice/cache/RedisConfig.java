package com.usc.csci401.goatservice.cache;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Bean(name = "redisTemplate")
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    setSerializer(redisTemplate);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  private void setSerializer(RedisTemplate<String, Object> template) {
    GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
        new GenericJackson2JsonRedisSerializer();
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(genericJackson2JsonRedisSerializer);
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
  }

}

package com.usc.csci401.goatservice.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  public void zadd(String key, Object value, double score){
    redisTemplate.opsForZSet().add(key, value, score);
  }

  public Long zsize(String key){
    return redisTemplate.opsForZSet().size(key);
  }


}

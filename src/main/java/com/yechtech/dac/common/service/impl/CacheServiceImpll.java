package com.yechtech.dac.common.service.impl;

import com.yechtech.dac.common.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author ChenYu
 * @create 2020-09-14
 * @tag I love java better than girl
 */
@Service
public class CacheServiceImpll implements CacheService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void zAdd(String key,String value,double score) {
        redisTemplate.opsForZSet().add(key, value,score);
    }


    @Override
    public Set<String> zRange(String key, long min, long max) {
        Set<String> result = redisTemplate.opsForZSet().range(key, min, max);
        return result;
    }
}

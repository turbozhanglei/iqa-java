package com.yechtech.dac.common.service;

import java.util.Set;

/**
 * @author ChenYu
 * @create 2020-09-14
 * @tag I love java better than girl
 */
public interface CacheService {

    void  zAdd(String key,String value,double score);

    Set<String> zRange(String key, long min, long max);
}

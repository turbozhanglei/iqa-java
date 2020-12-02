package com.yechtech.dac.common.function;

import com.yechtech.dac.common.exception.RedisConnectException;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}

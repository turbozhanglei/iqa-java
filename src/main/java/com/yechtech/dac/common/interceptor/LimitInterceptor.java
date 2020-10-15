package com.yechtech.dac.common.interceptor;

import com.yechtech.dac.common.domain.Constants;
import com.yechtech.dac.common.service.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @author ChenYu
 * @create 2020-09-14
 * @tag I love java better than girl
 */
public class LimitInterceptor implements HandlerInterceptor {

    @Resource
    private CacheService cacheService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //拿到token
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)){
            String key=token.substring(token.length()-9,token.length()-1);
            //获取当前的时间戳
            long nowTime = System.currentTimeMillis();
            //获取1分钟前的时间戳
            long beforeOneMinute=nowTime-1000*60;
            //存入redis的zSet
            cacheService.zAdd(Constants.ZADD_CACHE_PREFIX+key,nowTime+"",(double)nowTime);
            //判断redis中在1分钟内值得个数
            Set<String> set = cacheService.zRange(Constants.ZADD_CACHE_PREFIX + key, beforeOneMinute, nowTime);
            //限定1分钟内同一用户访问接口次数不能超过10次
            if (set.size()>10){
                // throw new DapException("请不要频繁访问接口");
                return false;
            }
        }
        return true;
    }
}

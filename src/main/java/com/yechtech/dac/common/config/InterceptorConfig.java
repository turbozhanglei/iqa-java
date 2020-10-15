package com.yechtech.dac.common.config;

import com.yechtech.dac.common.interceptor.LimitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ChenYu
 * @create 2020-09-14
 * @tag I love java better than girl
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LimitInterceptor getLimitInterceptor(){
        return new LimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLimitInterceptor())
                .addPathPatterns("/api/*")
                .excludePathPatterns();
    }
}

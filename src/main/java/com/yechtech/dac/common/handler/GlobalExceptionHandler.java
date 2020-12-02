package com.yechtech.dac.common.handler;

import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.exception.DapException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DacResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息：", e);
        return new DacResponse().message("系统内部异常");
    }

    @ExceptionHandler(value = DapException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DacResponse handleDacException(DapException e) {
        log.warn("系统错误: {}", e.getMessage());
        return new DacResponse().message(e.getMessage());
    }

}

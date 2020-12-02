package com.yechtech.dac.common.feign.decoder;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.yechtech.dac.common.config.FeignConfig;
import com.yechtech.dac.common.exception.DapException;
import com.yechtech.dac.common.feign.dto.TableauError;
import com.yechtech.dac.common.feign.dto.TableauErrorResponse;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static feign.FeignException.errorStatus;

@Slf4j
public class TableauErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() != FeignConfig.SUCCESS_CODE) {
            log.error("feign request error ------------- begin");
            log.error("request url [{}]",response.request().url());
            TableauErrorResponse error = new TableauErrorResponse();
            try {
                if(ObjectUtils.isNotNull(response.body())){
                    String res = Util.toString(response.body().asReader());
                    log.error("response body:[{}]",res);
                    if(StringUtils.isNotBlank(res)){
                        error  = JSON.parseObject(res, TableauErrorResponse.class);
                    }
                }
            } catch (IOException e) {
                log.error("get response body failed!");
            }
            if(ObjectUtils.isNull(error.getError())){
                TableauError tableauError = new TableauError();
                if(response.status() == FeignConfig.UNAUTH_CODE){
                    tableauError.setDetail("tableau Unauthorized exception");
                }else{
                    tableauError.setDetail(String.format("request tableau error with status %s",response.status()));
                }
                error.setError(tableauError);
            }
            log.error("feign  request error ------------- end");
            return  new DapException(String.format("request report server error: %s",error.getError().getDetail()));
        }
        return errorStatus(methodKey, response);
    }
}

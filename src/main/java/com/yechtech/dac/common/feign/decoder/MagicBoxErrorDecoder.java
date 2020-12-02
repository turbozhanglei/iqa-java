package com.yechtech.dac.common.feign.decoder;

import com.yechtech.dac.common.config.FeignConfig;
import com.yechtech.dac.common.exception.DapException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static feign.FeignException.errorStatus;

@Slf4j
public class MagicBoxErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() != FeignConfig.SUCCESS_CODE) {
            log.error("feign request error ------------- begin");
            log.error("request url [{}] request method [{}]",response.request().url(),response.request().httpMethod());
            String res = "";
            try {
                 res = Util.toString(response.body().asReader());
                log.error("response body:[{}]",res);
            } catch (IOException e) {
                log.error("get response body failed!");
            }
            log.error("feign  request error ------------- end");
            return  new DapException(String.format("request report server failed with response: %s", res));
        }
        return errorStatus(methodKey, response);
    }
}

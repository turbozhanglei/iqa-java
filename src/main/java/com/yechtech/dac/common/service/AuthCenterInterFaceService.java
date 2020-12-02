package com.yechtech.dac.common.service;


import feign.Param;
import feign.RequestLine;

/**
 * @author dou
 */

public interface AuthCenterInterFaceService {

    // 调用SICC接口获取psid，用于生产环境
    @RequestLine("POST /getPsidRoleFromIframeToken")
    Object getPrdAccessToken(@Param("token") String token);

}

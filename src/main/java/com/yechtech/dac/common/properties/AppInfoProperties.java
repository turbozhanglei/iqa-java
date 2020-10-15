package com.yechtech.dac.common.properties;

import lombok.Data;

@Data
public class AppInfoProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String baseUri;
//    private String getAccessTokenUrl;
//    private String getUserInfoUrl;
    private String programId;
    private String appCode;
}

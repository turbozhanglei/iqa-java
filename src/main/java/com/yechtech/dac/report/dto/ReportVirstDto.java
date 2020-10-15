package com.yechtech.dac.report.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportVirstDto implements Serializable {

    private static final long serialVersionUID = 1903419134023958627L;
    /**
     * 报表id
     */
    private Long reportId;
    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * code码
     */
    private String code;

    /**
     * 签名规则
     */
    private String signature;

}

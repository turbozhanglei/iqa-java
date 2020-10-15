package com.yechtech.dac.report.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysTypeDto implements Serializable {

    private static final long serialVersionUID = 1903419134023958627L;
    /**
     * 类别 Id 当 typeId=0 时加载顶层
     */
    private Integer typeId;

    /**
     * 页码
     */
    private Integer pageNum ;

    /**
     * 页大小(滑动一次加载数据项)
     */
    private Integer pageSize;


    /**
     * 访问令牌
     */
    private String accessToken;


    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 编码
     */
    private String code;


    /**
     * 签名,规则
     */
    private String signature;
}

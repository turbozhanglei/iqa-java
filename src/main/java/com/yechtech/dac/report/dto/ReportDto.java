package com.yechtech.dac.report.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportDto implements Serializable {

    private static final long serialVersionUID = 1903419134023958627L;
    /**
     * 操作类型 0 点击系统分类 1 收藏分类 2 置顶
     */
    private Integer optType;

    /**
     * 类别Id
     */
    private Long typeId;

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

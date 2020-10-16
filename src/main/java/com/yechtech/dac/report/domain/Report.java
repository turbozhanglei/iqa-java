package com.yechtech.dac.report.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Report implements Serializable {
    private static final long serialVersionUID = 1903419134023958627L;

    /**
     * 报表图片
     */
    private String reportImg;

    /**
     * 报表名称
     */
    private String reportName;


    /**
     * 标签名
     */
    private String[]  tag;


    /**
     * 收藏状态 1#已收藏 0#未收藏
     */
    private Integer  isCollect;

    /**
     * 最新更新时间
     */
    private String  lastUpdateTime;


}

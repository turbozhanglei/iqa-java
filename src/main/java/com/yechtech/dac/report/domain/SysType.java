package com.yechtech.dac.report.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysType implements Serializable {
    private static final long serialVersionUID = 1903419134023958627L;

    /**
     * 分类id
     */
    private Integer typeId;

    /**
     * 上分类id
     */
    private Integer pTypeId;

    /**
     * 分类名称
     */
    private String  typeName;
}

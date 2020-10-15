package com.yechtech.dac.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = 1903419134023958627L;

    private Integer pageSize;
    private Integer pageNum;

    private String sortField;
    private String sortOrder;

    private String keyword;


}

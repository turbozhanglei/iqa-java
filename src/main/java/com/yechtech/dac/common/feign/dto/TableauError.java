package com.yechtech.dac.common.feign.dto;

import lombok.Data;

@Data
public class TableauError {
    private String summary;
    private String detail;
    private String code;
}

package com.yechtech.dac.traceability.enums;

import java.util.HashMap;

/**
 *  流程操作类型
 */
public enum StatusType {
    /**
     *  流程操作类型...
     */
    Zero(0, "有数据"),
    One(1,"无数据");





    StatusType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

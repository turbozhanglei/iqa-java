package com.yechtech.dac.traceability.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PageResult<T> implements Serializable {

    private long total;

    private List<T> data;

    private String message;

}

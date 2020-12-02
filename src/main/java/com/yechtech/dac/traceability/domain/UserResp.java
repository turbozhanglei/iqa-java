package com.yechtech.dac.traceability.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description:返回response
 * @author:Turbozhang
 * @createTime:2020/11/24 11:20
 * @version:1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserResp implements Serializable {
    @ApiModelProperty(notes = "tokenIqa")
    private String tokenIqa;
}

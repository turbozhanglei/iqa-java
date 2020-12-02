package com.yechtech.dac.traceability.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description:
 * @author:Turbozhang
 * @createTime:2020/11/24 11:14
 * @version:1.0
 */
@Getter
@Setter
@Data
public class UserDto implements Serializable {
    @ApiModelProperty(notes = "用户token信息")
    private String token;
}

package com.yechtech.dac.traceability.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 筛选条件request
 * @author : zxl
 * @email : 
 * @since : 2020-11-11 02:20:13
 * @version : v1.0.0
 */
@Getter
@Setter
@Data
public class FilterConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "品项名称")
    private String skuName;
    @ApiModelProperty(notes = "生产商名称")
    private String manufacturerName;
    @ApiModelProperty(notes = "生产商名称")
    private String rmiSkuName;
    @ApiModelProperty(notes = "原料生产商名称")
    private String manufacturerRmiName;
    @ApiModelProperty(notes = "品牌名称")
    private String brandNameCn;
    @ApiModelProperty(notes = "产品名称")
    private String productName;
    @ApiModelProperty(notes = "类型")
    private Integer type;
    @ApiModelProperty(notes = "用户token")
    private String tokenIqa;


}
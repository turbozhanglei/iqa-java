package com.yechtech.dac.traceability.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 批次追溯主档数据request
 * @author : zxl
 * @email : 
 * @since : 2020-11-11 02:20:13
 * @version : v1.0.0
 */
@Getter
@Setter
@Data
public class BatchTraceabilityBaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "供应商JDE编码")
    private String supplierJdecode;
    @ApiModelProperty(notes = "品项JDE编码")
    private String skuJdecode;
    @ApiModelProperty(notes = "生产日期")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date productionDate;
    @ApiModelProperty(notes = "生产商EQA编码")
    private String manufacturerEqaCode;
    @ApiModelProperty(notes = "页码",required = true)
    private Integer pageNum;
    @ApiModelProperty(notes = "页大小",required = true)
    private Integer pageSize;
    @ApiModelProperty(notes = "用户token")
    private String tokenIqa;
    @ApiModelProperty(notes = "权限标识psid")
    private Integer psid;


}
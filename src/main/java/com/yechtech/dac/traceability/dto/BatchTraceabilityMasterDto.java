package com.yechtech.dac.traceability.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class BatchTraceabilityMasterDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "品项名称")
    private String skuName;
    @ApiModelProperty(notes = "原料生产商名称")
    private String manufacturerRmiName;
    @ApiModelProperty(notes = "生产日期")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date productionDate;
    @ApiModelProperty(notes = "原料批品项名称")
    private String rmiSkuName;
    @ApiModelProperty(notes = "原料批品项编码")
    private String rmiSkuCode;
    @ApiModelProperty(notes = "原料批次号")
    private String rmiBatchNo;
    @ApiModelProperty(notes = "供应商EQA编码")
    private String supplierEqaCode;
    @ApiModelProperty(notes = "供应商JDE编码")
    private String supplierJdeCode;
    @ApiModelProperty(notes = "供应商名称")
    private String supplierName;
    @ApiModelProperty(notes = "生产商EQA编码")
    private String manufacturerEqaCode;
    @ApiModelProperty(notes = "生产商名称")
    private String manufacturerName;
    @ApiModelProperty(notes = "品项EQA编码")
    private String skuEqaCode;
    @ApiModelProperty(notes = "品项JDE编码")
    private String skuJdeCode;
    @ApiModelProperty(notes = "餐厅代码")
    private String storeCode;
    @ApiModelProperty(notes = "餐厅品牌")
    private String restBrand;
    @ApiModelProperty(notes = "产品名称")
    private String productName;
    @ApiModelProperty(notes = "页码",required = true)
    private Integer pageNum;
    @ApiModelProperty(notes = "页大小",required = true)
    private Integer pageSize;
    @ApiModelProperty(notes = "用户token")
    private String tokenIqa;
    @ApiModelProperty(notes = "生产日期")
    private String startTime;
    @ApiModelProperty(notes = "生产日期")
    private String endTime;
    private String psid;

}
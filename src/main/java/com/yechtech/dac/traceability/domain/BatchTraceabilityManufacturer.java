package com.yechtech.dac.traceability.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 生产商信息表
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:00
 * @version : v1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("cdm_fact_iqa_batch_traceability_manufacturer")
public class BatchTraceabilityManufacturer extends Model<BatchTraceabilityManufacturer> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "上游原料Id")
    private Long id;
    @ApiModelProperty(notes = "生产商EQA编码")
    private String manufacturerEqacode;
    @ApiModelProperty(notes = "生产商名称")
    private String manufacturerName;
    @ApiModelProperty(notes = "品项EQA编码")
    private String skuEqacode;
    @ApiModelProperty(notes = "品项JDE编码")
    private String skuJdecode;
    @ApiModelProperty(notes = "品项名称")
    private String skuName;
    @ApiModelProperty(notes = "生产日期")
    private Date productionDate;
    @ApiModelProperty(notes = "生产数量")
    private Integer productionQuantity;
    @ApiModelProperty(notes = "发货日期")
    private Date shipDate;
    @ApiModelProperty(notes = "发货数量")
    private Integer shipmentQuantity;
    @ApiModelProperty(notes = "收货人")
    private String receiver;
    @ApiModelProperty(notes = "psid")
    private String psid;
    @ApiModelProperty(notes = "psid")
    private String roleCode;
    @ApiModelProperty(notes = "psid")
    private String supplierJdecode;

    
}
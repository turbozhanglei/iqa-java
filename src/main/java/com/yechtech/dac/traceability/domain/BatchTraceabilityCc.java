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
 * 整合中心表
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:19:48
 * @version : v1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("cdm_fact_iqa_batch_traceability_cc")
public class BatchTraceabilityCc extends Model<BatchTraceabilityCc> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "上游原料Id")
    private Long id;
    @ApiModelProperty(notes = "供应商JDE编码")
    private String supplierJdecode;
    @ApiModelProperty(notes = "供应商名称")
    private String supplierName;
    @ApiModelProperty(notes = "品项EQA编码")
    private String skuEqacode;
    @ApiModelProperty(notes = "品项JDE编码")
    private String skuJdecode;
    @ApiModelProperty(notes = "品项名称")
    private String skuName;
    @ApiModelProperty(notes = "生产日期")
    private Date productionDate;
    @ApiModelProperty(notes = "整合中心编码")
    private String slcCodeCc;
    @ApiModelProperty(notes = "整合中心名称")
    private String slcNameCc;
    @ApiModelProperty(notes = "最早到货日期")
    private String earliestdelvDate;
    @ApiModelProperty(notes = "出货至")
    private String receiver;
    @ApiModelProperty(notes = "到货数量")
    private Integer receivedQty;
    @ApiModelProperty(notes = "出货数量")
    private Integer shipmentQty;
    @ApiModelProperty(notes = "在库数量")
    private Integer inventoriesQty;
    @ApiModelProperty(notes = "采购单位")
    private String uom;
    @ApiModelProperty(notes = "出货code")
    private String receiverCode;
    @ApiModelProperty(notes = "psid")
    private String psid;
    
}
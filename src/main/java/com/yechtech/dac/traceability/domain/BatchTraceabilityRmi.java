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
 * 上游原料表
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:25
 * @version : v1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("cdm_fact_iqa_batch_traceability_rmi")
public class BatchTraceabilityRmi extends Model<BatchTraceabilityRmi> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "上游原料Id")
    private Long id;
    @ApiModelProperty(notes = "原料生产商编码")
    private String manufacturerRmiCode;
    @ApiModelProperty(notes = "原料生产商名称")
    private String manufacturerRmiName;
    @ApiModelProperty(notes = "原料批品项编码")
    private String rmiSkuCode;
    @ApiModelProperty(notes = "原料批品项名称")
    private String rmiSkuName;
    @ApiModelProperty(notes = "原料批次号")
    private String rmiBatchNo;
    @ApiModelProperty(notes = "生产商EQA编码")
    private String manufacturerEqaCode;
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
    @ApiModelProperty(notes = "投料量")
    private Integer quantity;
    @ApiModelProperty(notes = "采购单位")
    private String uom;
    @ApiModelProperty(notes = "psid")
    private String psid;
    
}
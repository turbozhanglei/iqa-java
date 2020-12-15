package com.yechtech.dac.traceability.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 批次追溯主档数据
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:13
 * @version : v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cdm_fact_iqa_batch_traceability_master_data")
public class BatchTraceabilityMasterData extends Model<BatchTraceabilityMasterData> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "主档数据Id")
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
    @ApiModelProperty(notes = "供应商EQA编码")
    private String supplierEqacode;
    @ApiModelProperty(notes = "供应商JDE编码")
    private String supplierJdecode;
    @ApiModelProperty(notes = "供应商名称")
    private String supplierName;
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
    @ApiModelProperty(notes = "生产量")
    private Integer productionQty;
    @ApiModelProperty(notes = "采购单位")
    private String uom;
    @ApiModelProperty(notes = "供应商发货数量")
    private Integer supplierShipmentQty;
    @ApiModelProperty(notes = "到货数量")
    private Integer receivedQty;
    @ApiModelProperty(notes = "出货数量")
    private Integer shipmentQty;
    @ApiModelProperty(notes = "在库数量")
    private Integer inventoriesQty;
    @ApiModelProperty(notes = "追溯率")
    private BigDecimal traceRatio;
    @ApiModelProperty(notes = "餐厅代码")
    private String storeCode;
    @ApiModelProperty(notes = "餐厅品牌")
    private String restBrand;
    @ApiModelProperty(notes = "餐厅品牌代码")
    private String restBrandCode;
    @ApiModelProperty(notes = "产品名称")
    private String productName;
    @ApiModelProperty(notes = "权限标识psid")
    private String psid;
    @ApiModelProperty(notes = "生产日期")
    private String startTime;
    @ApiModelProperty(notes = "生产日期")
    private String endTime;

    
}
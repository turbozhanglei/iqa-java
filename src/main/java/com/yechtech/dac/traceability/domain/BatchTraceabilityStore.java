package com.yechtech.dac.traceability.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 餐厅信息表
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:41
 * @version : v1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("cdm_fact_iqa_batch_traceability_store")
public class BatchTraceabilityStore extends Model<BatchTraceabilityStore> implements Serializable {
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
    @ApiModelProperty(notes = "餐厅代码")
    private String storeCode;
    @ApiModelProperty(notes = "餐厅名称")
    private String storeName;
    @ApiModelProperty(notes = "品牌")
    private String brandNameCn;
    @ApiModelProperty(notes = "FQA市场编码")
    private String fqaMarketcode;
    @ApiModelProperty(notes = "FQA市场名称")
    private String fqaMarketname;
    @ApiModelProperty(notes = "来源物流中心编码")
    private String slcCodeLc;
    @ApiModelProperty(notes = "来源物流中心名称")
    private String slcNameLc;
    @ApiModelProperty(notes = "最早到货日期")
    private Date earliestdelvdate;
    @ApiModelProperty(notes = "最晚到货日期")
    private Date latestdelvdate;
    @ApiModelProperty(notes = "到货数量")
    private BigDecimal quantity;
    @ApiModelProperty(notes = "采购单位")
    private String uom;
    @ApiModelProperty(notes = "psid")
    private String psid;
    @ApiModelProperty(notes = "roleCode")
    private String roleCode;


}
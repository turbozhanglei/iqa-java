package com.yechtech.dac.traceability.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author : 
 * @email : 
 * @since : 2021-01-04 04:50:34
 * @version : v1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("ods_iqa_manu_sku_rel")
public class OdsIqaManuSkuRel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "记录ID")
    private String id;
    @ApiModelProperty(notes = "生产商ID")
    private String manufacturerId;
    @ApiModelProperty(notes = "供应商EQA编码")
    private String manufacturerEqacode;
    @ApiModelProperty(notes = "品项ID")
    private String skuId;
    @ApiModelProperty(notes = "品项JDE编码")
    private String skuJdecode;
    @ApiModelProperty(notes = "状态位")
    private String status;
    @ApiModelProperty(notes = "创建人")
    private String creator;
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    @ApiModelProperty(notes = "更新人")
    private String updator;
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
    @ApiModelProperty(notes = "生产商名称")
    private String manufacturerCnName;
    
}
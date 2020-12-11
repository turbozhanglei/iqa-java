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
 * dim生产商
 * @author : 
 * @email : 
 * @since : 2020-12-10 01:44:36
 * @version : v1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("edw_dim_manufacturer")
public class EdwDimanufacturer extends Model<EdwDimanufacturer> implements Serializable  {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "生产商id")
    private String manufacturerId;
    @ApiModelProperty(notes = "生产商EQA code")
    private String manufacturerEqaCode;
    @ApiModelProperty(notes = "生产商中文名")
    private String manufacturerCnName;
    @ApiModelProperty(notes = "生产商英文名")
    private String manufacturernameEnName;
    @ApiModelProperty(notes = "注册地址")
    private String registerAddress;
    @ApiModelProperty(notes = "QA/EN 团队名称")
    private String qaTeamEnName;
    @ApiModelProperty(notes = "生产商级别")
    private String manufacturerLevel;
    @ApiModelProperty(notes = "省份")
    private String provinceCnName;
    @ApiModelProperty(notes = "城市")
    private String cityCnName;
    @ApiModelProperty(notes = "生产地址")
    private String factoryAddress;
    @ApiModelProperty(notes = "营业执照中文名")
    private String businessLicenseCnName;
    @ApiModelProperty(notes = "营业执照失效日期")
    private String businessLicenseExpireDate;
    @ApiModelProperty(notes = "生产商认证状态")
    private String authenticationStatus;
    @ApiModelProperty(notes = "生产商状态")
    private String status;
    @ApiModelProperty(notes = "采购类型")
    private String purchaseType;
    @ApiModelProperty(notes = "主供品类code")
    private String mainSubcategoryCode;
    @ApiModelProperty(notes = "主供品类名称")
    private String mainSubcategoryCnName;
    @ApiModelProperty(notes = "主供品项EQA code")
    private String mainSkuEqaCode;
    @ApiModelProperty(notes = "主供品项Jde code")
    private String mainSkuJdeCode;
    @ApiModelProperty(notes = "主供品项名称")
    private String mainSkuCnName;
    @ApiModelProperty(notes = "创建时间")
    private String createTime;
    @ApiModelProperty(notes = "修改时间")
    private String updateTime;
    @ApiModelProperty(notes = "操作人")
    private String operator;
    @ApiModelProperty(notes = "结束原因描述")
    private String closureReasonDesc;
    @ApiModelProperty(notes = "生产商属性描述")
    private String propertyDesc;
    @ApiModelProperty(notes = "产品类型")
    private String productType;
    @ApiModelProperty(notes = "顶层类别代码")
    private String topCategoryCode;
    @ApiModelProperty(notes = "生效日期")
    private String effectiveDate;
    @ApiModelProperty(notes = "失效日期")
    private String expiredDate;
    @ApiModelProperty(notes = "最新标识")
    private String newFlag;
    @ApiModelProperty(notes = "数据创建人")
    private String dwCreateBy;
    @ApiModelProperty(notes = "数据创建时间")
    private String dwCreateDate;
    @ApiModelProperty(notes = "数据更新人")
    private String dwUpdateBy;
    @ApiModelProperty(notes = "数据更新时间")
    private String dwUpdateDate;
    @ApiModelProperty(notes = "id")
    private Long id;

}
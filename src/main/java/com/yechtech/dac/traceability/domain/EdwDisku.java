package com.yechtech.dac.traceability.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * dim-SKU
 * @author : 
 * @email : 
 * @since : 2020-12-10 02:22:55
 * @version : v1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("edw_dim_sku")
public class EdwDisku extends Model<EdwDisku> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "品项id")
    private String skuId;
    @ApiModelProperty(notes = "品项短编码")
    private String skuCodeShort;
    @ApiModelProperty(notes = "品项编码")
    private String skuCode;
    @ApiModelProperty(notes = "品项编码3")
    private String skuCode3;
    @ApiModelProperty(notes = "品项英文说明")
    private String skuDescriptionEn;
    @ApiModelProperty(notes = "品项名称")
    private String skuName;
    @ApiModelProperty(notes = "规格")
    private String skuStandards;
    @ApiModelProperty(notes = "销售类别码1")
    private String salesreportingcode1;
    @ApiModelProperty(notes = "销售类别码1描述")
    private String salesreportingcode1Desc;
    @ApiModelProperty(notes = "SlsCd3代码")
    private String salesreportingcode3;
    @ApiModelProperty(notes = "SlsCd3描述")
    private String salesreportingcode3Desc;
    @ApiModelProperty(notes = "销售类别码4")
    private String salesreportingcode4;
    @ApiModelProperty(notes = "销售类别码4描述")
    private String salesreportingcode4Desc;
    @ApiModelProperty(notes = "销售类别码5")
    private String salesreportingcode5;
    @ApiModelProperty(notes = "销售类别码5描述")
    private String salesreportingcode5Desc;
    @ApiModelProperty(notes = "销售类别码6")
    private String salesreportingcode6;
    @ApiModelProperty(notes = "销售类别码7")
    private String salesreportingcode7;
    @ApiModelProperty(notes = "Cat Cd9")
    private String salesreportingcode9;
    @ApiModelProperty(notes = "是否下传到EP")
    private String issendep;
    @ApiModelProperty(notes = "价格是否同步到JDE")
    private String issendpricejde;
    @ApiModelProperty(notes = "OSXSM")
    private String isosxsm;
    @ApiModelProperty(notes = "设备小件价格表")
    private String isoel;
    @ApiModelProperty(notes = "YUM允收期")
    private String mrs;
    @ApiModelProperty(notes = "保质期")
    private String qgdt;
    @ApiModelProperty(notes = "LC允收期")
    private String lcmrs;
    @ApiModelProperty(notes = "餐厅允收期")
    private String rtmrs;
    @ApiModelProperty(notes = "栈板堆码")
    private String plcd;
    @ApiModelProperty(notes = "eQA编码")
    private String eqano;
    @ApiModelProperty(notes = "税率")
    private String tax;
    @ApiModelProperty(notes = "条形码")
    private String barcord;
    @ApiModelProperty(notes = "保质期临期预警天数")
    private String dayqgned;
    @ApiModelProperty(notes = "保质期禁售天数")
    private String dayqgfsd;
    @ApiModelProperty(notes = "新增的品牌码")
    private String newCj;
    @ApiModelProperty(notes = "特许权费比率")
    private String rateRoyal;
    @ApiModelProperty(notes = "商品分类")
    private String purchasingreportcode1;
    @ApiModelProperty(notes = "商品分类描述")
    private String purchasingreportcode1Desc;
    @ApiModelProperty(notes = "商品子类")
    private String purchasingreportcode2;
    @ApiModelProperty(notes = "商品子类描述")
    private String purchasingreportcode2Desc;
    @ApiModelProperty(notes = "计划族")
    private String purchasingreportcode4;
    @ApiModelProperty(notes = "特殊分类")
    private String specialClassification;
    @ApiModelProperty(notes = "商品码")
    private String commoditycode;
    @ApiModelProperty(notes = "一揽子重定价组")
    private String repricebasketpricecat;
    @ApiModelProperty(notes = "一揽子重定价组描述")
    private String repricebasketpricecatDesc;
    @ApiModelProperty(notes = "采购人号")
    private String buyer;
    @ApiModelProperty(notes = "采购人号描述")
    private String buyerName;
    @ApiModelProperty(notes = "UM(主要单位或最小单位)")
    private String unitofmeasureprimary;
    @ApiModelProperty(notes = "UM名称")
    private String unitofmeasureprimaryName;
    @ApiModelProperty(notes = "次要计量单位")
    private String unitofmeasuresecondary;
    @ApiModelProperty(notes = "次要计量单位名称")
    private String unitofmeasuresecondaryName;
    @ApiModelProperty(notes = "采购单位")
    private String unitofmeasurepurchas;
    @ApiModelProperty(notes = "采购单位名称")
    private String unitofmeasurepurchasName;
    @ApiModelProperty(notes = "定价单位")
    private String unitofmeasurepricing;
    @ApiModelProperty(notes = "定价单位名称")
    private String unitofmeasurepricingName;
    @ApiModelProperty(notes = "发运单位")
    private String unitofmeasureshipping;
    @ApiModelProperty(notes = "发运单位名称")
    private String unitofmeasureshippingName;
    @ApiModelProperty(notes = "部件单位")
    private String unitofmeasureallocation;
    @ApiModelProperty(notes = "部件单位名称")
    private String unitofmeasureallocationName;
    @ApiModelProperty(notes = "总帐类别")
    private String glcategory;
    @ApiModelProperty(notes = "总帐类别描述")
    private String glcategoryDesc;
    @ApiModelProperty(notes = "P品项标识")
    private String stockingtype;
    @ApiModelProperty(notes = "Z品项Code")
    private String skuCodZ;
    @ApiModelProperty(notes = "本级 提前期")
    private String leadtimelevel;
    @ApiModelProperty(notes = "计划时段 规则")
    private String plantimefencerule;
    @ApiModelProperty(notes = "计划时段")
    private BigDecimal planningtimefencedays;
    @ApiModelProperty(notes = "冻结时段")
    private BigDecimal freezetimefencedays;
    @ApiModelProperty(notes = "消息时段")
    private BigDecimal msgtimefencedays;
    @ApiModelProperty(notes = "品项EQACode")
    private String skuEqacode;
    @ApiModelProperty(notes = "品项大类EQACode")
    private String categoryEqacode;
    @ApiModelProperty(notes = "大类名称")
    private String categoryname;
    @ApiModelProperty(notes = "品项小类EQACode")
    private String subcategoryEqacode;
    @ApiModelProperty(notes = "子类名称")
    private String subcategoryname;
    @ApiModelProperty(notes = "品项状态")
    private String status;
    @ApiModelProperty(notes = "品牌属性")
    private String brand;
    @ApiModelProperty(notes = "QA/EN Team")
    private String qaEnTeamname;
    @ApiModelProperty(notes = "品项属性")
    private String property;
    @ApiModelProperty(notes = "食品安全风险")
    private String riskfoodsec;
    @ApiModelProperty(notes = "加工复杂性")
    private String complexity;
    @ApiModelProperty(notes = "风险等级")
    private String risklevel;
    @ApiModelProperty(notes = "质量重要性")
    private String qualityimportance;
    @ApiModelProperty(notes = "食品风险等级")
    private String foodrisklevel;
    @ApiModelProperty(notes = "一级品类描述")
    private String categoryName1;
    @ApiModelProperty(notes = "二级品类描述")
    private String categoryName2;
    @ApiModelProperty(notes = "三级品类描述")
    private String categoryName3;
    @ApiModelProperty(notes = "四级品类描述")
    private String categoryName4;
    @ApiModelProperty(notes = "五级品类描述")
    private String categoryName5;
    @ApiModelProperty(notes = "一级品类代码")
    private String categoryCode1;
    @ApiModelProperty(notes = "二级品类代码")
    private String categoryCode2;
    @ApiModelProperty(notes = "三级品类代码")
    private String categoryCode3;
    @ApiModelProperty(notes = "四级品类代码")
    private String categoryCode4;
    @ApiModelProperty(notes = "五级品类代码")
    private String categoryCode5;
    @ApiModelProperty(notes = "BOM代码")
    private String fitcode;
    @ApiModelProperty(notes = "SKU创建日期")
    private String skuCreateDate;
    @ApiModelProperty(notes = "品项进口标识")
    private String skuImportflag;
    @ApiModelProperty(notes = "品项区分(百胜自营，时胜达)")
    private String brandScreening;
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
    @ApiModelProperty(notes = "")
    private String eqaInternalorimport;
    @ApiModelProperty(notes = "eqa食品大类")
    private String eqaFoodCategory;
    @ApiModelProperty(notes = "eqa食品小类")
    private String eqaFoodSubcategory;
    @ApiModelProperty(notes = "id")
    private Long id;
    @ApiModelProperty(notes = "psid")
    private String psid;
    @ApiModelProperty(notes = "roleCode")
    private String roleCode;

}
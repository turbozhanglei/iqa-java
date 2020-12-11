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
 * dim-餐厅
 * @author : 
 * @email : 
 * @since : 2020-12-10 02:23:23
 * @version : v1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("edw_dim_store")
public class EdwDistore extends Model<EdwDistore> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "餐厅代理主键")
    private String storeId;
    @ApiModelProperty(notes = "公司")
    private String companyCode;
    @ApiModelProperty(notes = "公司名称")
    private String companyName;
    @ApiModelProperty(notes = "餐厅代码")
    private String storeCode;
    @ApiModelProperty(notes = "BU 类型")
    private String buType;
    @ApiModelProperty(notes = "餐厅名称")
    private String storeName;
    @ApiModelProperty(notes = "品牌")
    private String brandCode;
    @ApiModelProperty(notes = "餐厅编号")
    private String storeHyperioncode;
    @ApiModelProperty(notes = "开票单位")
    private String invoiceCode;
    @ApiModelProperty(notes = "开票单位名称")
    private String invoiceName;
    @ApiModelProperty(notes = "搜索类型")
    private String searchType;
    @ApiModelProperty(notes = "个人/ 公司")
    private String individualsCompanies;
    @ApiModelProperty(notes = "类别码")
    private String categoryCode;
    @ApiModelProperty(notes = "类别码 21")
    private String categoryCode21;
    @ApiModelProperty(notes = "地址")
    private String storeAddress;
    @ApiModelProperty(notes = "邮政编码")
    private String postCode;
    @ApiModelProperty(notes = "市")
    private String city;
    @ApiModelProperty(notes = "省")
    private String state;
    @ApiModelProperty(notes = "国家")
    private String country;
    @ApiModelProperty(notes = "营运市场")
    private String opsMarketJde;
    @ApiModelProperty(notes = "财务市场")
    private String financeMarket;
    @ApiModelProperty(notes = "法人市场")
    private String legalpersonmarket;
    @ApiModelProperty(notes = "Open ClosureAttr")
    private String openClosureattr;
    @ApiModelProperty(notes = "财务市场开业日期")
    private String openDate;
    @ApiModelProperty(notes = "财务市场闭店日期")
    private String closeDate;
    @ApiModelProperty(notes = "区块 键")
    private String blockChain;
    @ApiModelProperty(notes = "SLC")
    private String slc;
    @ApiModelProperty(notes = "拥有方式")
    private String ownerShip;
    @ApiModelProperty(notes = "逻辑仓id")
    private String lcId;
    @ApiModelProperty(notes = "逻辑仓Code")
    private String lcCode;
    @ApiModelProperty(notes = "餐厅中文简称")
    private String storeShortNameCn;
    @ApiModelProperty(notes = "餐厅英文简称")
    private String storeShortNameEn;
    @ApiModelProperty(notes = "商圈编码")
    private String tradeZoneCode;
    @ApiModelProperty(notes = "商圈名称")
    private String tradeZoneName;
    @ApiModelProperty(notes = "税率")
    private String taxRate;
    @ApiModelProperty(notes = "法人公司编码(发票抬头)")
    private String corporateCompanyCode;
    @ApiModelProperty(notes = "财务市场名称")
    private String financeMarketName;
    @ApiModelProperty(notes = "拥有方式名称")
    private String ownerShipName;
    @ApiModelProperty(notes = "品牌中文名称")
    private String brandNameCn;
    @ApiModelProperty(notes = "品牌英文名称")
    private String brandNameEn;
    @ApiModelProperty(notes = "HR所属城市编号")
    private String hrCityCode;
    @ApiModelProperty(notes = "HR所属城市名称")
    private String hrCityName;
    @ApiModelProperty(notes = "JDE营运市场名称")
    private String opsMarketNameJde;
    @ApiModelProperty(notes = "餐厅详细地址")
    private String storeDetailAddress;
    @ApiModelProperty(notes = "餐厅电话")
    private String storeTelphone;
    @ApiModelProperty(notes = "邮编")
    private String postalCode;
    @ApiModelProperty(notes = "电子邮箱")
    private String mail;
    @ApiModelProperty(notes = "传真")
    private String fax;
    @ApiModelProperty(notes = "地级市")
    private String cityCounty;
    @ApiModelProperty(notes = "楼层")
    private String floorNum;
    @ApiModelProperty(notes = "餐厅位置编码")
    private String positionCode;
    @ApiModelProperty(notes = "供消费者联系电话")
    private String consumerUsePhone;
    @ApiModelProperty(notes = "营运餐厅名称")
    private String opsStoreName;
    @ApiModelProperty(notes = "区县")
    private String countyLevelCity;
    @ApiModelProperty(notes = "PS城市")
    private String psCity;
    @ApiModelProperty(notes = "PS城市中文名称")
    private String psCityNameCn;
    @ApiModelProperty(notes = "PS城市英文名称")
    private String psCityNameEn;
    @ApiModelProperty(notes = "城市类别")
    private String cityCategory;
    @ApiModelProperty(notes = "城市类别中文名称")
    private String cityCategoryNameCn;
    @ApiModelProperty(notes = "城市类别中文简称")
    private String cityCategoryShortNameCn;
    @ApiModelProperty(notes = "城市类别英文")
    private String cityCategoryNameEn;
    @ApiModelProperty(notes = "城市类别英文简称")
    private String cityCategoryShortNameEn;
    @ApiModelProperty(notes = "餐厅内场电话")
    private String storeTelInfield;
    @ApiModelProperty(notes = "餐厅外场电话")
    private String storeTelOutfileld;
    @ApiModelProperty(notes = "CMS-详细地址")
    private String cmsStoreAddress;
    @ApiModelProperty(notes = "CMS-省/市/区")
    private String cmsRegionalismCode;
    @ApiModelProperty(notes = "CMS-省名称")
    private String cmsRegionalismProName;
    @ApiModelProperty(notes = "CMS-市名称")
    private String cmsRegionalismCityName;
    @ApiModelProperty(notes = "CMS-区名称")
    private String cmsRegionalismAreaName;
    @ApiModelProperty(notes = "餐厅类型")
    private String storeTypeCode;
    @ApiModelProperty(notes = "餐厅类型名称")
    private String storeTypeName;
    @ApiModelProperty(notes = "营运开店日期")
    private String startBusinessDate;
    @ApiModelProperty(notes = "营运闭店日期")
    private String endBusinessDate;
    @ApiModelProperty(notes = "进货方式")
    private String restockStyleCode;
    @ApiModelProperty(notes = "进货方式名称")
    private String restockStyleName;
    @ApiModelProperty(notes = "BOH营运市场")
    private String bohOpsMarketCode;
    @ApiModelProperty(notes = "BOH营运市场名称")
    private String bohOpsMarketName;
    @ApiModelProperty(notes = "母店店号")
    private String parentStoreCode;
    @ApiModelProperty(notes = "营业状态")
    private String ieTypeCode;
    @ApiModelProperty(notes = "营业状态名称")
    private String ieTypeName;
    @ApiModelProperty(notes = "营业状态开始日期")
    private String ieStartDate;
    @ApiModelProperty(notes = "营业状态结束日期")
    private String ieEndDate;
    @ApiModelProperty(notes = "机车类型")
    private String locoCode;
    @ApiModelProperty(notes = "机车类型名称")
    private String locoName;
    @ApiModelProperty(notes = "面团制作类型")
    private String doughMakeCode;
    @ApiModelProperty(notes = "面团制作类型名称")
    private String doughMakeName;
    @ApiModelProperty(notes = "早餐TA")
    private String breakfastTa;
    @ApiModelProperty(notes = "正餐TA")
    private String dinnerTa;
    @ApiModelProperty(notes = "IT市场")
    private String itMarketCode;
    @ApiModelProperty(notes = "IT市场名称")
    private String itMarketName;
    @ApiModelProperty(notes = "餐厅位置名称")
    private String positionName;
    @ApiModelProperty(notes = "楼层(排班)")
    private String workStyleCode;
    @ApiModelProperty(notes = "楼层(排班)名称")
    private String workStyleName;
    @ApiModelProperty(notes = "经度")
    private BigDecimal longitude;
    @ApiModelProperty(notes = "纬度")
    private BigDecimal latitude;
    @ApiModelProperty(notes = "PMP服务公司名称")
    private String pmpservername;
    @ApiModelProperty(notes = "是否需要微检覆盖")
    private String microbeneed;
    @ApiModelProperty(notes = "PMP服务公司ID")
    private String microbeserverid;
    @ApiModelProperty(notes = "微生物检测公司名称")
    private String microbeservername;
    @ApiModelProperty(notes = "FQA市场代码")
    private String fqamarketcode;
    @ApiModelProperty(notes = "FQA市场名称")
    private String fqamarketname;
    @ApiModelProperty(notes = "认领人")
    private String ownername;
    @ApiModelProperty(notes = "认领状态")
    private String status;
    @ApiModelProperty(notes = "是否需要微检覆盖")
    private String isenabledrestaurant;
    @ApiModelProperty(notes = "生效日期")
    private String effectiveDate;
    @ApiModelProperty(notes = "失效日期")
    private String expiredDate;
    @ApiModelProperty(notes = "最新标识")
    private String newFlag;
    @ApiModelProperty(notes = "eqa营运市场")
    private String eqaOpmarket;
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
package com.yechtech.dac.traceability.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 批次追溯主档数据
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:13
 * @version : v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FilterConditionData extends Model<FilterConditionData> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "不重复主键")
    private String lable;
    @ApiModelProperty(notes = "返回值")
    private String value;

}
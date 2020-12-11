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
 * 
 * @author : 
 * @email : 
 * @since : 2020-12-10 02:23:42
 * @version : v1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("edw_iqa_master_data_rmi")
public class EdwIqaMasterDataRmi extends Model<EdwIqaMasterDataRmi> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "数据类型")
    private String dataType;
    @ApiModelProperty(notes = "数据编码")
    private String dataCode;
    @ApiModelProperty(notes = "数据值")
    private String dataValue;
    @ApiModelProperty(notes = "id")
    private Long id;

}
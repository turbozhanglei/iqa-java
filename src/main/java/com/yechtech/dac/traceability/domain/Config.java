package com.yechtech.dac.traceability.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description:
 * @author:Turbozhang
 * @createTime:2020/12/2 11:16
 * @version:1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("cdm_config")
public class Config extends Model<Config> implements Serializable {
    @ApiModelProperty(notes = "key")
    private String iqakey;
    @ApiModelProperty(notes = "value")
    private String iqavalue;
}

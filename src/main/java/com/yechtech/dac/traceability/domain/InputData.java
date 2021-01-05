package com.yechtech.dac.traceability.domain;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:Turbozhang
 * @createTime:2021/1/4 17:27
 * @version:1.0
 */
@Data
public class InputData {
    List<FilterConditionData> responseList;
    List<FilterConditionData> linkList;
}

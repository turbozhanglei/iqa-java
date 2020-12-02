package com.yechtech.dac.traceability.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.BatchTraceabilityDetailedSummary;
import com.yechtech.dac.traceability.domain.BatchTraceabilityManufacturer;
import com.yechtech.dac.traceability.domain.DetailedSummary;
import org.apache.ibatis.annotations.Mapper;


/**
 * 批次追溯明细数据表
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:13
 * @version : v1.0.0
 */
@Mapper
public interface BatchTraceabilityDetailedSummaryMapper extends BaseMapper<DetailedSummary> {


}
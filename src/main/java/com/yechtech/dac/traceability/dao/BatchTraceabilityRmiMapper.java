package com.yechtech.dac.traceability.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.BatchTraceabilityDetailedSummary;
import com.yechtech.dac.traceability.domain.BatchTraceabilityRmi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 上游原料表
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:13
 * @version : v1.0.0
 */
@Mapper
public interface BatchTraceabilityRmiMapper extends BaseMapper<BatchTraceabilityRmi> {

    List<BatchTraceabilityRmi> query(BatchTraceabilityRmi queryRmi);

    List<BatchTraceabilityRmi> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("cdfactIqaBatchTraceabilityRmi") BatchTraceabilityRmi cdfactIqaBatchTraceabilityRmi);

    long queryPageCount(BatchTraceabilityRmi cdfactIqaBatchTraceabilityRmi);
}
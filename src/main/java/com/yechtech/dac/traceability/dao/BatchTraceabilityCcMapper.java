package com.yechtech.dac.traceability.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.BatchTraceabilityCc;
import com.yechtech.dac.traceability.domain.BatchTraceabilityRmi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 整合中心
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:13
 * @version : v1.0.0
 */
@Mapper
public interface BatchTraceabilityCcMapper extends BaseMapper<BatchTraceabilityCc> {

    List<BatchTraceabilityCc> query(BatchTraceabilityCc bilityCc);

    List<BatchTraceabilityCc> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("cdfactIqaBatchTraceabilityCc") BatchTraceabilityCc batchTraceabilityStore);

    long queryPageCount(BatchTraceabilityCc batchTraceabilityStore);

    List<BatchTraceabilityCc> queryList(BatchTraceabilityCc bilityCc);
}
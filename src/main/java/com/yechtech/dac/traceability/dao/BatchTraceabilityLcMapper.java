package com.yechtech.dac.traceability.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.BatchTraceabilityLc;
import com.yechtech.dac.traceability.domain.BatchTraceabilityRmi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 物流中心
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:13
 * @version : v1.0.0
 */
@Mapper
public interface BatchTraceabilityLcMapper extends BaseMapper<BatchTraceabilityLc> {


    List<BatchTraceabilityLc> query(BatchTraceabilityLc traceabilityLc);

    List<BatchTraceabilityLc> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("cdfactIqaBatchTraceabilityLc") BatchTraceabilityLc batchTraceabilityStore);

    long queryPageCount(BatchTraceabilityLc batchTraceabilityStore);
}
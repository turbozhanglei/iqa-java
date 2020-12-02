package com.yechtech.dac.traceability.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.BatchTraceabilityManufacturer;
import com.yechtech.dac.traceability.domain.BatchTraceabilityMasterData;
import com.yechtech.dac.traceability.domain.BatchTraceabilityRmi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 批次追溯主档数据表
 * @author : 
 * @email : 
 * @since : 2020-11-11 02:20:13
 * @version : v1.0.0
 */
@Mapper
public interface BatchTraceabilityManufacturerMapper extends BaseMapper<BatchTraceabilityManufacturer> {

    List<BatchTraceabilityManufacturer> query(BatchTraceabilityManufacturer manufacturer);

    List<BatchTraceabilityManufacturer> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("cdfactIqaBatchTraceabilityManufacturer") BatchTraceabilityManufacturer cdfactIqaBatchTraceabilityManufacturer);

    long queryPageCount(BatchTraceabilityManufacturer cdfactIqaBatchTraceabilityManufacturer);
}
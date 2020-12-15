package com.yechtech.dac.traceability.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.BatchTraceabilityMasterData;
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
public interface BatchTraceabilityMasterDataMapper extends BaseMapper<BatchTraceabilityMasterData> {

    List<BatchTraceabilityMasterData>query(@Param("cdfactIqaBatchTraceabilityMasterData") BatchTraceabilityMasterData cdfactIqaBatchTraceabilityMasterData);

    List<BatchTraceabilityMasterData> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("cdfactIqaBatchTraceabilityMasterData") BatchTraceabilityMasterData cdfactIqaBatchTraceabilityMasterData);

    long queryPageCount(BatchTraceabilityMasterData cdfactIqaBatchTraceabilityMasterData);

}
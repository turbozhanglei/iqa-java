package com.yechtech.dac.traceability.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.BatchTraceabilityCc;
import com.yechtech.dac.traceability.domain.BatchTraceabilityRmi;
import com.yechtech.dac.traceability.domain.BatchTraceabilityStore;
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
public interface BatchTraceabilityStoreMapper extends BaseMapper<BatchTraceabilityStore> {

    List<BatchTraceabilityStore> query(BatchTraceabilityStore traceabilityStore);

    List<BatchTraceabilityStore> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("BatchTraceabilityStore") BatchTraceabilityStore batchTraceabilityStore);

    long queryPageCount(BatchTraceabilityStore batchTraceabilityStore);

    void insertInfo(BatchTraceabilityStore batchTraceabilityStore);

    List<BatchTraceabilityStore> queryList(BatchTraceabilityStore traceabilityStore);
}
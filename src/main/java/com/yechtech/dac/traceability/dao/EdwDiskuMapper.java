package com.yechtech.dac.traceability.dao;

import java.lang.Long;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.EdwDisku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * dim-SKU
 * @author : 
 * @email : 
 * @since : 2020-12-10 02:22:55
 * @version : v1.0.0
 */
@Mapper
public interface  EdwDiskuMapper extends BaseMapper<EdwDisku> {


    EdwDisku queryByPrimaryKey(Long id);

    List<EdwDisku> query(EdwDisku edwDisku);

    List<EdwDisku> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("edwDisku") EdwDisku edwDisku);

    long queryPageCount(EdwDisku edwDisku);
}
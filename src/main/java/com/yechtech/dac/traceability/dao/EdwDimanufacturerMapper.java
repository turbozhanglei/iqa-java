package com.yechtech.dac.traceability.dao;

import java.lang.Long;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.EdwDimanufacturer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * dim生产商
 * @author : 
 * @email : 
 * @since : 2020-12-10 01:44:36
 * @version : v1.0.0
 */
@Mapper
public interface  EdwDimanufacturerMapper extends BaseMapper<EdwDimanufacturer> {



    long update(EdwDimanufacturer edwDimanufacturer);

    EdwDimanufacturer queryByPrimaryKey(Long id);

    List<EdwDimanufacturer> query(EdwDimanufacturer edwDimanufacturer);

    List<EdwDimanufacturer> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("edwDimanufacturer") EdwDimanufacturer edwDimanufacturer);

    long queryPageCount(EdwDimanufacturer edwDimanufacturer);
}
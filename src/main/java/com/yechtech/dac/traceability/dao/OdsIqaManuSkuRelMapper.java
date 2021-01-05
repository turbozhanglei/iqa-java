package com.yechtech.dac.traceability.dao;

import java.lang.Long;
import java.util.List;

import com.yechtech.dac.traceability.domain.OdsIqaManuSkuRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author : 
 * @email : 
 * @since : 2021-01-04 04:50:34
 * @version : v1.0.0
 */
@Mapper
public interface  OdsIqaManuSkuRelMapper {

    OdsIqaManuSkuRel queryByPrimaryKey(Long id);

    List<OdsIqaManuSkuRel> query(OdsIqaManuSkuRel odsIqaManuSkuRel);

    List<OdsIqaManuSkuRel> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("odsIqaManuSkuRel") OdsIqaManuSkuRel odsIqaManuSkuRel);

    long queryPageCount(OdsIqaManuSkuRel odsIqaManuSkuRel);
}
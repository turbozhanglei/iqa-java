package com.yechtech.dac.traceability.dao;

import java.lang.Long;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.EdwDimanufacturer;
import com.yechtech.dac.traceability.domain.EdwDistore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * dim-餐厅
 * @author : 
 * @email : 
 * @since : 2020-12-10 02:23:23
 * @version : v1.0.0
 */
@Mapper
public interface  EdwDistoreMapper extends BaseMapper<EdwDistore> {

    EdwDistore queryByPrimaryKey(Long id);

    List<EdwDistore> query(EdwDistore edwDistore);

    List<EdwDistore> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("edwDistore") EdwDistore edwDistore);

    long queryPageCount(EdwDistore edwDistore);
}
package com.yechtech.dac.traceability.dao;


import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yechtech.dac.traceability.domain.EdwIqaMasterDataRmi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author : 
 * @email : 
 * @since : 2020-12-10 02:23:42
 * @version : v1.0.0
 */
@Mapper
public interface EdwIqaMasterDataRmiMapper extends BaseMapper<EdwIqaMasterDataRmi> {

    List<EdwIqaMasterDataRmi> query(EdwIqaMasterDataRmi edwIqaMasterDataRmi);

    List<EdwIqaMasterDataRmi> queryPage(@Param("startIndex") int startIndex, @Param("limit") int limit, @Param("edwIqaMasterDataRmi") EdwIqaMasterDataRmi edwIqaMasterDataRmi);

    long queryPageCount(EdwIqaMasterDataRmi edwIqaMasterDataRmi);
}
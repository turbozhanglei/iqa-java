package com.yechtech.dac.common.service;


import feign.Param;
import feign.RequestLine;

/**
 * @author dou
 */

public interface AuthCenterInterFaceService {

    /*获取报表分类*/
    @RequestLine("GET /api/report/center/tree")
    Object getTree();

    /*根据分类获取报表*/
    @RequestLine("GET  /api/report?pageSize={pageSize}&pageNum={pageNum}&keyword={keyword}&reportCenter={reportCenter}&category={category}")
    Object getReport(@Param("pageSize")Integer pageSize,@Param("pageNum")Integer pageNum,@Param("keyword")String keyword, @Param("category")Long category,@Param("reportCenter")Boolean reportCenter);

    /*根据报表id查询报表信息*/
    @RequestLine("GET  /api/report/{rId}")
    Object getReport(@Param("rId") Long rId, @Param("reportCenter")Boolean reportCenter);


}

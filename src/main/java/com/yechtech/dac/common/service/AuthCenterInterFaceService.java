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
    @RequestLine("GET  /api/report/{rId}?reportCenter={reportCenter}")
    Object getReport(@Param("rId") Long rId, @Param("reportCenter")Boolean reportCenter);

    /*获取所有标签*/
    @RequestLine("GET  /api/user/tags")
    Object getFavorites();


    /*新增标签*/
    @RequestLine("POST  /api/user/tags")
    Object addFavorites(@Param("tagName") String tagName);

    /*编辑标签*/
    @RequestLine("PUT  /api/user/tags/{tagId}")
    Object editFavorites(@Param("tagName") String tagName,@Param("tagId") Long tagId,@Param("id") Long id);


    /*删除标签*/
    @RequestLine("DELETE  /api/user/tags/{tagId}")
    Object delFavorites(@Param("isForce") String isForce, @Param("tagId") Long tagId);

    /*收藏报表*/
    @RequestLine("POST  /api/reports/{rId}/collect")
    Object collect(@Param("rId") Long rId, @Param("id") Long id);


    /*取消收藏*/
    @RequestLine("POST  /api/reports/{rId}/disCollect")
    Object delCollect(@Param("rId") Long rId);

}

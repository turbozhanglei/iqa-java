package com.yechtech.dac.collect.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FavoritesDto implements Serializable {

    private static final long serialVersionUID = 1903419134023958627L;
    /**
     *  访问令牌
     */
    private String accessToken;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 编码
     */
    private String code;


    /**
     * 签名,规则
     */
    private String signature;

    /**
     *  标签名称
     */
    private String favoriteName;

    /**
     *  标签id
     */
    private Long favoriteId;

    /**
     *  报表id
     */
    private Long reportId;

    /**
     *  是否删除标签
     */
    private String isForce  ;

    /**
     *  1 取消收藏 0 收藏
     */
    private Integer isCollect;
}

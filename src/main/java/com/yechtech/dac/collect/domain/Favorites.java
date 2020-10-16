package com.yechtech.dac.collect.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Favorites implements Serializable {

    private static final long serialVersionUID = 1903419134023958627L;
    /**
     *  标签id
     */
    private Long favoriteID;

    /**
     * 标签名称
     */
    private String favoriteName;

}

package com.yechtech.dac.collect.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yechtech.dac.collect.domain.Favorites;
import com.yechtech.dac.collect.dto.FavoritesDto;
import com.yechtech.dac.collect.service.FavoritesService;
import com.yechtech.dac.common.config.FeignConfig;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.exception.DapException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FavoritesServiceImpl implements FavoritesService {

    @Autowired
    private FeignConfig feignConfig;

    @Override
    public List<Favorites> selectList(FavoritesDto favoritesDto) {
        log.info("开始进入获取所有标签： 请求参数=============>>>>{}", JSONObject.toJSONString(favoritesDto));
        //调用dap服务，获取数据
        List<Favorites> list = new ArrayList<>();
        Object connectReport = feignConfig.getDapFegin(favoritesDto.getAccessToken()).getFavorites();
        if (ObjectUtils.isNotEmpty(connectReport)) {
            JSONObject jsonObject = JSONObject.parseObject(connectReport.toString());
            if (jsonObject.get("code").toString().equals("200") && ObjectUtils.isNotEmpty(jsonObject.get("data"))) {
                JSONArray jsonArray = JSONObject.parseObject(jsonObject.toJSONString()).getJSONArray("data");
                if (!CollectionUtils.isEmpty(jsonArray)) {
                    jsonArray.forEach(item -> {
                        Map map = JSONObject.parseObject(JSONObject.toJSONString(item), Map.class);
                        Favorites favorites = new Favorites();
                        favorites.setFavoriteID(Long.valueOf(map.get("id").toString()));
                        favorites.setFavoriteName((String) map.get("tagName"));
                        list.add(favorites);
                    });
                }
            }
        }
        return list;
    }


    @Override
    public DacResponse add(FavoritesDto favoritesDto) {
        log.info("开始进入新增标签： 请求参数=============>>>>{}", JSONObject.toJSONString(favoritesDto));
        try {
            //调用dap服务
            Object connectReport = feignConfig.getDapFegin(favoritesDto.getAccessToken()).addFavorites(favoritesDto.getFavoriteName());
            if (ObjectUtils.isNotEmpty(connectReport)) {
                JSONObject jsonObject = JSONObject.parseObject(connectReport.toString());
                if (!jsonObject.get("code").toString().equals("200")) {
                    throw new DapException(jsonObject.get("message").toString());
                }
            }
        } catch (Exception e) {
            log.error("新增标签失败...{}", e.getMessage());
            return new DacResponse().message("新增标签失败...");
        }
        return new DacResponse();
    }


    @Override
    public DacResponse mod(FavoritesDto favoritesDto) {
        log.info("开始进入编辑标签： 请求参数=============>>>>{}", JSONObject.toJSONString(favoritesDto));

        try {
            //调用dap服务
            Object connectReport = feignConfig.getDapFegin(favoritesDto.getAccessToken()).editFavorites(favoritesDto.getFavoriteName(),
                    favoritesDto.getFavoriteId(), favoritesDto.getFavoriteId());
            if (ObjectUtils.isNotEmpty(connectReport)) {
                JSONObject jsonObject = JSONObject.parseObject(connectReport.toString());
                if (!jsonObject.get("code").toString().equals("200")) {
                    throw new DapException(jsonObject.get("message").toString());
                }
            }

        } catch (Exception e) {
            log.error("编辑标签失败...{}", e.getMessage());
            return new DacResponse().message("编辑标签失败...");
        }
        return new DacResponse();
    }

    @Override
    public DacResponse del(@RequestBody FavoritesDto favoritesDto) {
        log.info("开始进入删除标签： 请求参数=============>>>>{}", JSONObject.toJSONString(favoritesDto));
        try {
            //调用dap服务
            Object connectReport = feignConfig.getDapFegin(favoritesDto.getAccessToken()).delFavorites(favoritesDto.getIsForce(), favoritesDto.getFavoriteId());
            if (ObjectUtils.isNotEmpty(connectReport)) {
                JSONObject jsonObject = JSONObject.parseObject(connectReport.toString());
                if (!jsonObject.get("code").toString().equals("200")) {
                    throw new DapException(jsonObject.get("message").toString());
                }
            }
        } catch (Exception e) {
            log.error("删除标签失败...{}", e.getMessage());
            return new DacResponse().message("删除标签失败...");
        }
        return new DacResponse();
    }


    @Override
    public DacResponse favoriteReport(FavoritesDto favoritesDto) {
        log.info("开始进入取消/收藏报表： 请求参数=============>>>>{}", JSONObject.toJSONString(favoritesDto));
        try {
            //isCollect 0代表收藏操作，1代表取消收藏操作
            if (favoritesDto.getIsCollect() == 0) {
                //调用dap服务
                Object connectReport = feignConfig.getDapFegin(favoritesDto.getAccessToken()).collect(favoritesDto.getReportId(), favoritesDto.getFavoriteId());
                if (ObjectUtils.isNotEmpty(connectReport)) {
                    JSONObject jsonObject = JSONObject.parseObject(connectReport.toString());
                    if (!jsonObject.get("code").toString().equals("200")) {
                        throw new DapException(jsonObject.get("message").toString());
                    }
                }
            } else {
                //调用dap服务
                Object connectReport = feignConfig.getDapFegin(favoritesDto.getAccessToken()).delCollect(favoritesDto.getReportId());
                if (ObjectUtils.isNotEmpty(connectReport)) {
                    JSONObject jsonObject = JSONObject.parseObject(connectReport.toString());
                    if (!jsonObject.get("code").toString().equals("200")) {
                        throw new DapException(jsonObject.get("message").toString());
                    }
                }
            }
        } catch (Exception e) {
            log.error("取消/收藏报表失败...{}", e.getMessage());
            return new DacResponse().message("取消/收藏报表失败...");
        }
        return new DacResponse();
    }
}

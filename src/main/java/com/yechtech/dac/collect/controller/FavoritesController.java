package com.yechtech.dac.collect.controller;

import com.yechtech.dac.collect.domain.Favorites;
import com.yechtech.dac.collect.dto.FavoritesDto;
import com.yechtech.dac.collect.service.FavoritesService;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.utils.CheckSignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class FavoritesController {

    @Autowired
    private FavoritesService favoritesService;

    /**
     * 获取收藏分类(getFavorites)
     *
     * @return
     */
    @GetMapping("/favorites")
    public DacResponse getFavorites(FavoritesDto favoritesDto) throws UnsupportedEncodingException {
        //验签失败，参数有问题
        if (!CheckSignatureUtil.check(favoritesDto.getCode(), favoritesDto.getTimestamp(), favoritesDto.getSignature())) {
            return new DacResponse().message("验签失败，参数异常");
        }
        List<Favorites> favorites = favoritesService.selectList(favoritesDto);
        return new DacResponse().data(favorites);
    }

    /**
     * 新增收藏分类(addFavorite)
     *
     * @return
     */
    @PostMapping("/favorites/add")
    public DacResponse addFavorite(@RequestBody FavoritesDto favoritesDto) throws UnsupportedEncodingException {
        //验签失败，参数有问题
        if (!CheckSignatureUtil.check(favoritesDto.getCode(), favoritesDto.getTimestamp(), favoritesDto.getSignature())) {
            return new DacResponse().message("验签失败，参数异常");
        }
        return favoritesService.add(favoritesDto);
    }

    /**
     * 编辑收藏分类(editFavorite)
     *
     * @return
     */
    @PutMapping("/favorites/mod")
    public DacResponse editFavorite(@RequestBody FavoritesDto favoritesDto) throws UnsupportedEncodingException {
        //验签失败，参数有问题
        if (!CheckSignatureUtil.check(favoritesDto.getCode(), favoritesDto.getTimestamp(), favoritesDto.getSignature())) {
            return new DacResponse().message("验签失败，参数异常");
        }
        return favoritesService.mod(favoritesDto);
    }


    /**
     * 删除收藏分类(delFavorite)
     *
     * @return
     */
    @DeleteMapping("/favorites/del")
    public DacResponse delFavorite(@RequestBody FavoritesDto favoritesDto) throws UnsupportedEncodingException {
        //验签失败，参数有问题
        if (!CheckSignatureUtil.check(favoritesDto.getCode(), favoritesDto.getTimestamp(), favoritesDto.getSignature())) {
            return new DacResponse().message("验签失败，参数异常 ");
        }
        return favoritesService.del(favoritesDto);
    }

    /**
     * 收藏取消报表(delFavorite)
     *
     * @return
     */
    @PostMapping("/favoritesReport")
    public DacResponse favoriteReport(@RequestBody FavoritesDto favoritesDto) throws UnsupportedEncodingException {
        //验签失败，参数有问题
        if (!CheckSignatureUtil.check(favoritesDto.getCode(), favoritesDto.getTimestamp(), favoritesDto.getSignature())) {
            return new DacResponse().message("验签失败，参数异常 ");
        }
        return favoritesService.favoriteReport(favoritesDto);
    }
}

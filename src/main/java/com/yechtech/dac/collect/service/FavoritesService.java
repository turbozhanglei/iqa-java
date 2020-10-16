package com.yechtech.dac.collect.service;

import com.yechtech.dac.collect.domain.Favorites;
import com.yechtech.dac.collect.dto.FavoritesDto;
import com.yechtech.dac.common.domain.DacResponse;

import java.util.List;


public interface FavoritesService {
    List<Favorites> selectList(FavoritesDto favoritesDto);

    DacResponse add(FavoritesDto favoritesDto);

    DacResponse mod(FavoritesDto favoritesDto);

    DacResponse del(FavoritesDto favoritesDto);

    DacResponse favoriteReport(FavoritesDto favoritesDto);

}

package com.doranco.synthsale.service;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.model.Favorite;
import com.doranco.synthsale.model.User;

import java.util.List;

public interface FavoriteService {
    void addFavorite(Long adId);
    List<Favorite> getFavoritesForUser();
    void removeFavorite(Long adId);
    boolean isAdInFavorites(User user, Ad ad);
}


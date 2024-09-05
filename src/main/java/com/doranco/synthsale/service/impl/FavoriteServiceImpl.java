package com.doranco.synthsale.service.impl;

import com.doranco.synthsale.model.Favorite;
import com.doranco.synthsale.model.User;
import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.repository.FavoriteRepository;
import com.doranco.synthsale.service.FavoriteService;
import com.doranco.synthsale.service.UserService;
import com.doranco.synthsale.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AdService adService;

    @Override
    public void addFavorite(Long adId) {
        User user = userService.getLoggedUser();
        Ad ad = adService.getAdById(adId);

        Optional<Favorite> existingFavorite = favoriteRepository.findByUserAndAd(user, ad);
        if (existingFavorite.isEmpty()) {
            Favorite favorite = new Favorite(user, ad);
            favoriteRepository.save(favorite);
        }
    }

    @Override
    public List<Favorite> getFavoritesForUser() {
        User user = userService.getLoggedUser();
        return favoriteRepository.findByUser(user);
    }

    @Override
    public void removeFavorite(Long adId) {
        User user = userService.getLoggedUser();
        Ad ad = adService.getAdById(adId);
        Optional<Favorite> favorite = favoriteRepository.findByUserAndAd(user, ad);
        favorite.ifPresent(favoriteRepository::delete);
    }

    @Override
    public boolean isAdInFavorites(User user, Ad ad) {
        return favoriteRepository.findByUserAndAd(user, ad).isPresent();
    }

}


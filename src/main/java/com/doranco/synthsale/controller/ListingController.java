package com.doranco.synthsale.controller;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.model.CategoryEnum;
import com.doranco.synthsale.model.Favorite;
import com.doranco.synthsale.service.AdService;
import com.doranco.synthsale.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ads")
public class ListingController {

    private final AdService adService;
    private final FavoriteService favoriteService;

    @Autowired
    public ListingController(AdService adService, FavoriteService favoriteService) {
        this.adService = adService;
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public String showAds(
            @RequestParam(value = "searchQuery", required = false) String searchQuery,
            @RequestParam(value = "category", required = false) String categoryParam,
            @RequestParam(value = "effects", required = false) Boolean effects,
            @RequestParam(value = "sqcr", required = false) Boolean sqcr,
            @RequestParam(value = "arp", required = false) Boolean arp,
            @RequestParam(value = "numberOfVoices", required = false) Integer numberOfVoices,
            @RequestParam(value = "oscPerVoice", required = false) Integer oscPerVoice,
            @RequestParam(value = "hardwareType", required = false) String hardwareType,
            @RequestParam(value = "multitimbral", required = false) String multitimbralParam,
            Model model) {

        CategoryEnum category = null;
        if (categoryParam != null) {
            try {
                category = CategoryEnum.valueOf(categoryParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", "Invalid category");
                return "listing";
            }
        }

        Integer multitimbral = 0;
        if (multitimbralParam != null && !multitimbralParam.isEmpty()) {
            try {
                multitimbral = Integer.valueOf(multitimbralParam);
            } catch (NumberFormatException e) {
                model.addAttribute("error", "Invalid multitimbral value");
                return "listing";
            }
        }

        List<Ad> ads;
        if (searchQuery == null && category == null && effects == null && sqcr == null && arp == null
                && numberOfVoices == null && oscPerVoice == null && hardwareType == null && multitimbral == 0) {
            ads = adService.getAllAds();
        } else {
            ads = adService.getAdsByFilters(
                    searchQuery, category, effects, sqcr, arp, numberOfVoices, oscPerVoice, hardwareType, multitimbral);
        }

        // Récupération des favoris de l'utilisateur
        List<Favorite> favorites = favoriteService.getFavoritesForUser();
        List<Long> favoriteAdIds = favorites.stream()
                .map(favorite -> favorite.getAd().getId())
                .collect(Collectors.toList());

        model.addAttribute("ads", ads);
        model.addAttribute("favoriteAdIds", favoriteAdIds);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("category", category);
        model.addAttribute("effects", effects);
        model.addAttribute("sqcr", sqcr);
        model.addAttribute("arp", arp);
        model.addAttribute("numberOfVoices", numberOfVoices);
        model.addAttribute("oscPerVoice", oscPerVoice);
        model.addAttribute("hardwareType", hardwareType);
        model.addAttribute("multitimbral", multitimbralParam);

        return "listing";
    }

    @GetMapping("/category/{categoryName}")
    public String showAdsByCategory(@PathVariable String categoryName, Model model) {
        CategoryEnum category = null;
        try {
            category = CategoryEnum.valueOf(categoryName.toUpperCase());
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid category");
            return "listing";
        }

        List<Ad> ads = adService.getAdsByFilters(null, category, null, null, null, null, null, null, null);

        // Récupération des favoris de l'utilisateur
        List<Favorite> favorites = favoriteService.getFavoritesForUser();
        List<Long> favoriteAdIds = favorites.stream()
                .map(favorite -> favorite.getAd().getId())
                .collect(Collectors.toList());

        model.addAttribute("ads", ads);
        model.addAttribute("favoriteAdIds", favoriteAdIds);
        model.addAttribute("category", category);

        return "listing";
    }
}
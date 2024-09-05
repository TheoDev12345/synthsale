package com.doranco.synthsale.controller;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.model.CategoryEnum;
import com.doranco.synthsale.model.User;
import com.doranco.synthsale.service.AdService;
import com.doranco.synthsale.service.FavoriteService;
import com.doranco.synthsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AdController {

    @Autowired
    private AdService adService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    @GetMapping("/filter")
    public String filterAds(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Boolean effects,
            @RequestParam(required = false) Boolean sqcr,
            @RequestParam(required = false) Boolean arp,
            @RequestParam(required = false) Integer numberOfVoices,
            @RequestParam(required = false) Integer oscPerVoice,
            @RequestParam(required = false) Integer multitimbral,
            @RequestParam(required = false) String hardwareType,
            Model model) {

        // Convertir la chaîne de caractères en CategoryEnum
        CategoryEnum category = null;
        if (categoryName != null) {
            category = CategoryEnum.valueOf(categoryName.toUpperCase());
        }

        if (multitimbral == null) {
            multitimbral = 0;
        }

        // Récupérer les annonces filtrées
        List<Ad> ads = adService.getAdsByFilters(searchQuery, category, effects, sqcr, arp, numberOfVoices, oscPerVoice, hardwareType, multitimbral);

        // Ajouter la liste des annonces au modèle
        model.addAttribute("ads", ads);

        return "listing";
    }



    @GetMapping("/ad/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        Ad ad = adService.getAdById(id);

        if (ad != null) {
            String formattedDate = ad.getDateCreation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            model.addAttribute("ad", ad);
            model.addAttribute("formattedDate", formattedDate);

            User seller = ad.getUser();
            model.addAttribute("seller", seller);

            // Récupérer l'utilisateur connecté
            User loggedUser = userService.getLoggedUser();

            // Vérifier si l'annonce est dans les favoris de l'utilisateur
            boolean isFavorite = favoriteService.isAdInFavorites(loggedUser, ad);
            model.addAttribute("isFavorite", isFavorite);

            return "product";
        } else {
            return "error/404";
        }
    }
}


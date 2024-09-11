package com.doranco.synthsale.controller;

import com.doranco.synthsale.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add/{id}")
    public String addFavorite(@PathVariable("id") Long adId, RedirectAttributes redirectAttributes) {
        favoriteService.addFavorite(adId);
        redirectAttributes.addFlashAttribute("successMessage", "Annonce ajoutée aux favoris");
        return "redirect:/ad/" + adId;
    }

    @GetMapping
    public String showFavorites(Model model) {
        model.addAttribute("favorites", favoriteService.getFavoritesForUser());
        return "favorites";
    }

    @PostMapping("/remove/{id}")
    public String removeFavorite(@PathVariable("id") Long adId) {
        favoriteService.removeFavorite(adId);
        return "redirect:/favorites";
    }
}

package com.doranco.synthsale.controller;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.model.Synth;
import com.doranco.synthsale.model.User;
import com.doranco.synthsale.service.AdService;
import com.doranco.synthsale.service.SynthService;
import com.doranco.synthsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/post-ad")
public class PostAdController {

    @Autowired
    private AdService adService;

    @Autowired
    private SynthService synthService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showPostAdForm(Model model) {
        model.addAttribute("ad", new Ad());
        model.addAttribute("synth", new Synth());
        return "postAd";
    }

    @PostMapping
    public String addAd(@ModelAttribute("ad") Ad ad, Principal principal, Model model) {
        try {
            if (ad.getSynth() == null) {
                ad.setSynth(new Synth());
            }

            // Vérifier que la catégorie est bien définie
            if (ad.getSynth().getCategory() == null) {
                model.addAttribute("errorMessage", "La catégorie est requise.");
                return "postAd";
            }

            // Copier la catégorie du synth à l'annonce
            ad.setCategory(ad.getSynth().getCategory());

            // Récupérer l'utilisateur connecté
            String username = principal.getName();
            User user = userService.findUserByUsername(username);
            ad.setUser(user);

            // Sauvegarder l'annonce et le synthétiseur associé
            synthService.saveSynth(ad.getSynth());
            adService.saveAd(ad);

            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "postAd"; // Retourne à la page d'ajout avec le message d'erreur
        }
    }
}

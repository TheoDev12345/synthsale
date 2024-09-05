package com.doranco.synthsale.controller;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.model.User;
import com.doranco.synthsale.service.AdService;
import com.doranco.synthsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/manageAds")
public class ManageAdsController {

    @Autowired
    private AdService adService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showMyAds(Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        List<Ad> ads = adService.getAdsByUser(user);

        // Ajouter les informations de l'utilisateur au modèle
        model.addAttribute("ads", ads);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "manageAds";
    }

    @GetMapping("/delete/{id}")
    public String deleteAd(@PathVariable Long id, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        Ad ad = adService.getAdById(id);
        if (ad != null && ad.getUser().equals(user)) {
            adService.deleteAd(id);
        }
        return "redirect:/manageAds";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());
        Ad ad = adService.getAdById(id);

        if (ad != null && ad.getUser().equals(user)) {
            model.addAttribute("ad", ad);
            return "editAd"; // Affiche la vue editAd.html pour modifier l'annonce
        } else {
            model.addAttribute("errorMessage", "Annonce non trouvée ou vous n'avez pas les droits pour la modifier.");
            return "redirect:/manageAds";
        }
    }



    @PostMapping("/update/{id}")
    public String updateAd(@PathVariable Long id,
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam double price,
                           @RequestParam String imageUrl1,
                           @RequestParam(required = false) String imageUrl2,
                           @RequestParam(required = false) String imageUrl3,
                           @RequestParam String hardwareType,
                           @RequestParam Integer numberOfVoices,
                           @RequestParam Integer oscPerVoice,
                           @RequestParam(required = false) String multitimbral,
                           @RequestParam(required = false) Boolean effects,
                           @RequestParam(required = false) Boolean sqcr,
                           @RequestParam(required = false) Boolean arp,
                           Principal principal,
                           Model model) {

        try {
            User user = userService.findUserByUsername(principal.getName());
            Ad ad = adService.getAdById(id);

            if (ad != null && ad.getUser().equals(user)) {
                // Mise à jour des informations de l'annonce
                ad.setTitle(title);
                ad.setDescription(description);
                ad.setPrice(price);
                ad.setImageUrl1(imageUrl1);
                ad.setImageUrl2(imageUrl2);
                ad.setImageUrl3(imageUrl3);

                // Mise à jour des caractéristiques du synthétiseur
                ad.getSynth().setHardwareType(hardwareType);
                ad.getSynth().setNumberOfVoices(numberOfVoices);
                ad.getSynth().setOscPerVoice(oscPerVoice);
                ad.getSynth().setMultitimbral(Integer.valueOf(multitimbral));
                ad.getSynth().setEffects(effects != null ? effects : false);
                ad.getSynth().setSqcr(sqcr != null ? sqcr : false);
                ad.getSynth().setArp(arp != null ? arp : false);

                adService.saveAd(ad); // Sauvegarde de l'annonce et des modifications
            } else {
                model.addAttribute("errorMessage", "Annonce non trouvée ou vous n'avez pas les droits pour la modifier.");
                return "editAd";
            }

            return "redirect:/manageAds";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "editAd"; // Retourne à la page d'édition avec le message d'erreur
        }
    }



}

package com.doranco.synthsale.controller;

import com.doranco.synthsale.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AdService adService;

    @Autowired
    public HomeController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("latestAds", adService.getLatestAds());
        return "home";
    }

}

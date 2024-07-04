package com.doranco.synthsale.controller;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    @Autowired
    private AdService adService;

    @GetMapping("/filter")
    public List<Ad> filterAds(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Boolean effects,
            @RequestParam(required = false) Boolean sqcr,
            @RequestParam(required = false) Boolean arp,
            @RequestParam(required = false) Integer numberOfVoices,
            @RequestParam(required = false) Integer oscPerVoice,
            @RequestParam(required = false) String hardwareType,
            @RequestParam(required = false) String multitimbral) {
        return adService.getAdsByFilters(categoryName, effects, sqcr, arp, numberOfVoices, oscPerVoice, hardwareType, multitimbral);
    }
}
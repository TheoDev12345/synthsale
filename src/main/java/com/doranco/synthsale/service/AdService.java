package com.doranco.synthsale.service;

import com.doranco.synthsale.model.Ad;

import java.util.List;

public interface AdService {
    List<Ad> getAdsByFilters(String categoryName, Boolean effects, Boolean sqcr, Boolean arp, Integer numberOfVoices, Integer oscPerVoice, String hardwareType, String multitimbral);
    void saveAd(Ad ad);
    void deleteAd(Long id);
}

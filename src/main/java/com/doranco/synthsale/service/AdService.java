package com.doranco.synthsale.service;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.model.CategoryEnum;
import com.doranco.synthsale.model.User;

import java.util.List;

public interface AdService {
    List<Ad> getAdsByFilters(String searchQuery, CategoryEnum category, Boolean effects, Boolean sqcr, Boolean arp, Integer numberOfVoices, Integer oscPerVoice, String hardwareType, Integer multitimbral);

    List<Ad> getAllAds();

    void saveAd(Ad ad);
    void deleteAd(Long id);
    List<Ad> getLatestAds();
    Ad getAdById(Long id);
    List<Ad> getAdsByUser(User user);  
    }
	


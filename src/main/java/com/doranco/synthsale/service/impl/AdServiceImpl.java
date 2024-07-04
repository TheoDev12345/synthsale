package com.doranco.synthsale.service.impl;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.repository.AdRepository;
import com.doranco.synthsale.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {
    @Autowired
    private AdRepository adRepository;

    @Override
    public List<Ad> getAdsByFilters(String categoryName, Boolean effects, Boolean sqcr, Boolean arp, Integer numberOfVoices, Integer oscPerVoice, String hardwareType, String multitimbral) {
        return adRepository.findByFilters(categoryName, effects, sqcr, arp, numberOfVoices, oscPerVoice, hardwareType, multitimbral);
    }

    @Override
    public void saveAd(Ad ad) {
        adRepository.save(ad);
    }

    @Override
    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }
}

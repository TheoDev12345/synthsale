package com.doranco.synthsale.service.impl;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.model.CategoryEnum;
import com.doranco.synthsale.model.User;
import com.doranco.synthsale.repository.AdRepository;
import com.doranco.synthsale.service.AdService;
import com.doranco.synthsale.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Override
    public List<Ad> getAdsByFilters(String searchQuery, CategoryEnum category, Boolean effects, Boolean sqcr, Boolean arp, Integer numberOfVoices, Integer oscPerVoice, String hardwareType, Integer multitimbral) {
        System.out.println("Filtering ads with parameters:");
        System.out.println("searchQuery: " + searchQuery);
        System.out.println("category: " + category);
        System.out.println("effects: " + effects);
        System.out.println("sqcr: " + sqcr);
        System.out.println("arp: " + arp);
        System.out.println("numberOfVoices: " + numberOfVoices);
        System.out.println("oscPerVoice: " + oscPerVoice);
        System.out.println("hardwareType: " + hardwareType);
        System.out.println("multitimbral: " + multitimbral);

        // Gestion du cas où searchQuery est null ou vide
        if (searchQuery != null && searchQuery.trim().isEmpty()) {
            searchQuery = null;
            System.out.println("searchQuery was empty, set to null");
        }

        String categoryString = (category != null) ? category.name() : null;
        System.out.println("Converted category to string: " + categoryString);

        List<Ad> results = adRepository.findByFiltersAndSearchQuery(searchQuery, categoryString, effects, sqcr, arp, numberOfVoices, oscPerVoice, hardwareType, multitimbral);
        System.out.println("SQL Query: " + results.toString());  // Ceci affichera la requête SQL générée
        System.out.println("Number of ads found: " + results.size());
        return results;
    }

    public List<Ad> getAds(String searchQuery, CategoryEnum category, Boolean effects, Boolean sqcr, Boolean arp, Integer numberOfVoices, Integer oscPerVoice, String hardwareType, Integer multitimbral) {
        // Vérifier si tous les filtres sont null ou vides
        if (searchQuery == null && category == null && effects == null && sqcr == null && arp == null
                && numberOfVoices == null && oscPerVoice == null && hardwareType == null && multitimbral == null) {
            return getAllAds();  // Appeler la méthode pour récupérer toutes les annonces
        } else {
            return getAdsByFilters(searchQuery, category, effects, sqcr, arp, numberOfVoices, oscPerVoice, hardwareType, multitimbral);
        }
    }

    @Override
    public List<Ad> getAllAds() {
        List<Ad> results = adRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreation"));
        return results;
    }


    @Override
    public void saveAd(Ad ad) {

        if (!InputValidator.isValidInput(ad.getTitle())||
                !InputValidator.isValidInput(ad.getDescription()) ||
                !InputValidator.isValidInput(ad.getSynth().getHardwareType()) ||
                !InputValidator.isValidInput(ad.getSynth().getCategory().toString()) ||
                !InputValidator.isValidInput(ad.getLocation())) {
            throw new IllegalArgumentException("L'entrée contient des caractères non autorisés.");
        }
        adRepository.save(ad);
    }


    @Override
    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }

    @Override
    public List<Ad> getLatestAds() {
        return adRepository.findTop10ByOrderByDateCreationDesc();
    }

    @Override
    public Ad getAdById(Long id) {
        return adRepository.findById(id).orElse(null);
    }

    @Override
    public List<Ad> getAdsByUser(User user) {
        return adRepository.findByUser(user);
    }



}

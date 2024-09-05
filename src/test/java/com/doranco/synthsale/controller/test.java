package com.doranco.synthsale.controller;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.model.CategoryEnum;
import com.doranco.synthsale.model.Synth;
import com.doranco.synthsale.model.User;
import com.doranco.synthsale.service.AdService;
import com.doranco.synthsale.service.SynthService;
import com.doranco.synthsale.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostAdControllerTest {

    @Mock
    private AdService adService;

    @Mock
    private SynthService synthService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private PostAdController postAdController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAd_Success() {
        // Given
        Ad ad = new Ad();
        Synth synth = new Synth();
        synth.setCategory(CategoryEnum.ANALOGIC); // Utilisez une catégorie valide
        ad.setSynth(synth);

        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(principal.getName()).thenReturn(username);
        when(userService.findUserByUsername(username)).thenReturn(user);

        // When
        String viewName = postAdController.addAd(ad, principal, model);

        // Then
        assertEquals("redirect:/", viewName);
        verify(synthService, times(1)).saveSynth(synth);
        verify(adService, times(1)).saveAd(ad);

        // Vérifiez que les setters ont été appelés correctement
        assertEquals(synth, ad.getSynth());
        assertEquals(user, ad.getUser());
        assertEquals(CategoryEnum.ANALOGIC, ad.getCategory());
    }

    @Test
    void testAddAd_MissingCategory() {
        // Given
        Ad ad = new Ad();
        ad.setSynth(new Synth()); // Synth sans catégorie

        when(principal.getName()).thenReturn("testUser");
        when(userService.findUserByUsername("testUser")).thenReturn(new User());

        // When
        String viewName = postAdController.addAd(ad, principal, model);

        // Then
        assertEquals("postAd", viewName);
        verify(model, times(1)).addAttribute(eq("errorMessage"), eq("La catégorie est requise."));
        verify(synthService, never()).saveSynth(any());
        verify(adService, never()).saveAd(any());
    }

    @Test
    void testAddAd_ThrowsIllegalArgumentException() {
        // Given
        Ad ad = new Ad();
        Synth synth = new Synth();
        synth.setCategory(CategoryEnum.ANALOGIC); // Utilisez une catégorie valide
        ad.setSynth(synth);

        when(principal.getName()).thenReturn("testUser");
        when(userService.findUserByUsername("testUser")).thenReturn(new User());

        doThrow(new IllegalArgumentException("Erreur lors de la sauvegarde")).when(adService).saveAd(any(Ad.class));

        // When
        String viewName = postAdController.addAd(ad, principal, model);

        // Then
        assertEquals("postAd", viewName);
        verify(model, times(1)).addAttribute(eq("errorMessage"), eq("Erreur lors de la sauvegarde"));
        verify(synthService, times(1)).saveSynth(synth);
        verify(adService, times(1)).saveAd(ad);
    }
}

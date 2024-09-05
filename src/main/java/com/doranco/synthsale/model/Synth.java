package com.doranco.synthsale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Synth {

    @Id
    @GeneratedValue
    private Long id;


    private String hardwareType;
    private int numberOfVoices;
    private int oscPerVoice;

    private Integer multitimbral;
    private boolean effects;
    private boolean sqcr;
    private boolean arp;

    @NotNull(message = "Renseignez une categorie")
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    public Synth(){

    }

    public Synth(Long id, String hardwareType, int numberOfVoices, int oscPerVoice, Integer multitimbral, boolean effects, boolean sqcr, boolean arp, CategoryEnum category) {
        this.id = id;
        this.hardwareType = hardwareType;
        this.numberOfVoices = numberOfVoices;
        this.oscPerVoice = oscPerVoice;
        this.multitimbral = multitimbral;
        this.effects = effects;
        this.sqcr = sqcr;
        this.arp = arp;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHardwareType() {
        return hardwareType;
    }

    public void setHardwareType(String hardwareType) {
        this.hardwareType = hardwareType;
    }

    public int getNumberOfVoices() {
        return numberOfVoices;
    }

    public void setNumberOfVoices(int numberOfVoices) {
        this.numberOfVoices = numberOfVoices;
    }

    public int getOscPerVoice() {
        return oscPerVoice;
    }

    public void setOscPerVoice(int oscPerVoice) {
        this.oscPerVoice = oscPerVoice;
    }

    public Integer getMultitimbral() {
        return multitimbral;
    }

    public void setMultitimbral(Integer multitimbral) {
        this.multitimbral = multitimbral;
    }

    public boolean isEffects() {
        return effects;
    }

    public void setEffects(boolean effects) {
        this.effects = effects;
    }

    public boolean isSqcr() {
        return sqcr;
    }

    public void setSqcr(boolean sqcr) {
        this.sqcr = sqcr;
    }

    public boolean isArp() {
        return arp;
    }

    public void setArp(boolean arp) {
        this.arp = arp;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}

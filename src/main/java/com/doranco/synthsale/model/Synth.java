package com.doranco.synthsale.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class Synth {
    @Id
    @GeneratedValue
    private Long id;
    private List<String> hardwareType;
    private int numberOfVoices;
    private int oscPerVoice;
    private List<String> multitimbral;
    private boolean effects;
    private boolean sqcr;
    private boolean arp;

    @ManyToOne
    private Category category;

    public Synth(){

    }

    public Synth(Long id, List<String> hardwareType, int numberOfVoices, int oscPerVoice, List<String> multitimbral, boolean effects, boolean sqcr, boolean arp, Category category) {
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

    public List<String> getHardwareType() {
        return hardwareType;
    }

    public void setHardwareType(List<String> hardwareType) {
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

    public List<String> getMultitimbral() {
        return multitimbral;
    }

    public void setMultitimbral(List<String> multitimbral) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

package com.doranco.synthsale.service;

import com.doranco.synthsale.model.Synth;

import java.util.List;

public interface SynthService {
    List<Synth> getAllSynths();
    Synth getSynthById(Long id);
    void saveSynth(Synth synth);
    void deleteSynth(Long id);
}

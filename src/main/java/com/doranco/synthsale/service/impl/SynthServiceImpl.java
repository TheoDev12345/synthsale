package com.doranco.synthsale.service.impl;

import com.doranco.synthsale.model.Synth;
import com.doranco.synthsale.repository.SynthRepository;
import com.doranco.synthsale.service.SynthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SynthServiceImpl implements SynthService {
    @Autowired
    private SynthRepository synthRepository;

    @Override
    public List<Synth> getAllSynths() {
        return synthRepository.findAll();
    }

    @Override
    public Synth getSynthById(Long id) {
        return synthRepository.findById(id).orElse(null);
    }

    @Override
    public void saveSynth(Synth synth) {
        synthRepository.save(synth);
    }

    @Override
    public void deleteSynth(Long id) {
        synthRepository.deleteById(id);
    }
}
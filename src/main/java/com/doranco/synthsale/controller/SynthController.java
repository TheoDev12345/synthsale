package com.doranco.synthsale.controller;

import com.doranco.synthsale.model.Synth;
import com.doranco.synthsale.service.SynthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
@RestController
@RequestMapping("/api/synths")
public class SynthController {
    @Autowired
    private SynthService synthService;

    @GetMapping
    public List<Synth> getAllSynths() {
        return synthService.getAllSynths();
    }

    @GetMapping("/{id}")
    public Synth getSynthById(@PathVariable Long id) {
        return synthService.getSynthById(id);
    }

    @PostMapping
    public void saveSynth(@RequestBody Synth synth) {
        synthService.saveSynth(synth);
    }

    @DeleteMapping("/{id}")
    public void deleteSynth(@PathVariable Long id) {
        synthService.deleteSynth(id);
    }
}

 */
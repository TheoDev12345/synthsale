package com.doranco.synthsale.repository;

import com.doranco.synthsale.model.Synth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SynthRepository extends JpaRepository<Synth, Long> {
}

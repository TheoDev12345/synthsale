package com.doranco.synthsale.repository;

import com.doranco.synthsale.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    @Query("SELECT a FROM Ad a WHERE (:categoryName IS NULL OR a.category.name = :categoryName) AND (:effects IS NULL OR a.synth.effects = :effects) AND (:sqcr IS NULL OR a.synth.sqcr = :sqcr) AND (:arp IS NULL OR a.synth.arp = :arp) AND (:numberOfVoices IS NULL OR a.synth.numberOfVoices = :numberOfVoices) AND (:oscPerVoice IS NULL OR a.synth.oscPerVoice = :oscPerVoice) AND (:hardwareType IS NULL OR :hardwareType MEMBER OF a.synth.hardwareType) AND (:multitimbral IS NULL OR :multitimbral MEMBER OF a.synth.multitimbral)")
    List<Ad> findByFilters(
            @Param("categoryName") String categoryName,
            @Param("effects") Boolean effects,
            @Param("sqcr") Boolean sqcr,
            @Param("arp") Boolean arp,
            @Param("numberOfVoices") Integer numberOfVoices,
            @Param("oscPerVoice") Integer oscPerVoice,
            @Param("hardwareType") String hardwareType,
            @Param("multitimbral") String multitimbral
    );
}

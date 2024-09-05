package com.doranco.synthsale.repository;

import com.doranco.synthsale.model.Ad;
import com.doranco.synthsale.model.CategoryEnum;
import com.doranco.synthsale.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
	@Query(value = "SELECT a.id, a.title, a.description, a.price, a.location, a.date_creation, " +
			"a.image_url1, a.image_url2, a.image_url3, a.category, a.user_id, " +
			"s.id as synth_id, s.hardware_type, s.number_of_voices, s.osc_per_voice, s.multitimbral, s.effects, s.sqcr, s.arp, s.category as synth_category " +
			"FROM ad a JOIN synth s ON s.id = a.synth_id WHERE " +
			"(:searchQuery IS NULL OR a.title LIKE CONCAT('%', :searchQuery, '%') OR " +
			"a.description LIKE CONCAT('%', :searchQuery, '%')) " +
			"AND (:category IS NULL OR a.category = :category) " +
			"AND (:effects IS NULL OR :effects = FALSE OR s.effects = :effects) " +
			"AND (:sqcr IS NULL OR :sqcr = FALSE OR s.sqcr = :sqcr) " +
			"AND (:arp IS NULL OR :arp = FALSE OR s.arp = :arp) " +
			"AND (:numberOfVoices IS NULL OR s.number_of_voices = :numberOfVoices) " +
			"AND (:oscPerVoice IS NULL OR s.osc_per_voice = :oscPerVoice) " +
			"AND (:hardwareType IS NULL OR s.hardware_type = :hardwareType) " +
			"AND (:multitimbral IS NULL OR s.multitimbral = :multitimbral)" +
			"ORDER BY a.date_creation DESC", // Pour trier de la plus recente (en haut de la page) à la plus ancienne
			nativeQuery = true)
	List<Ad> findByFiltersAndSearchQuery(
			@Param("searchQuery") String searchQuery,
			@Param("category") String category,
			@Param("effects") Boolean effects,
			@Param("sqcr") Boolean sqcr,
			@Param("arp") Boolean arp,
			@Param("numberOfVoices") Integer numberOfVoices,
			@Param("oscPerVoice") Integer oscPerVoice,
			@Param("hardwareType") String hardwareType,
			@Param("multitimbral") Integer multitimbral
	);




	List<Ad> findTop10ByOrderByDateCreationDesc();
    
    List<Ad> findByUser(User user);
}

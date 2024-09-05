package com.doranco.synthsale.repository;

import com.doranco.synthsale.model.Favorite;
import com.doranco.synthsale.model.User;
import com.doranco.synthsale.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    Optional<Favorite> findByUserAndAd(User user, Ad ad);
}


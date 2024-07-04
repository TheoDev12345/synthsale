package com.doranco.synthsale.repository;

import com.doranco.synthsale.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
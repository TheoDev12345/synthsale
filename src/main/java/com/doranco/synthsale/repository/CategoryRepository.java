package com.doranco.synthsale.repository;

import com.doranco.synthsale.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

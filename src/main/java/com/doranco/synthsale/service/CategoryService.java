package com.doranco.synthsale.service;

import com.doranco.synthsale.model.Category;
import com.doranco.synthsale.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> getAllCategories();

}

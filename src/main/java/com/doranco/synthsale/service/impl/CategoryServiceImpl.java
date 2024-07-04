package com.doranco.synthsale.service.impl;

import com.doranco.synthsale.model.Category;
import com.doranco.synthsale.repository.CategoryRepository;
import com.doranco.synthsale.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}

package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.Category;
import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(Category category){return categoryRepository.save(category); }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}

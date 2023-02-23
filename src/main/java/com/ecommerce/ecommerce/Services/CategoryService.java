package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.Category;
import com.ecommerce.ecommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(Category category){return categoryRepository.save(category); }

    public List<Category> getAllCategories(){
        return categoryRepository.findCategoryByProducts();
    }
    public List<Category> getAdminAllCategories(){
        return categoryRepository.findAll();
    }
    public Category update(Long id, Category category){
        Optional<Category> entity=categoryRepository.findById(id);
        entity.get().setType(category.getType());
        categoryRepository.save(entity.get());
        return entity.get();
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

    public Optional< Category> findByID(Long id){
        return categoryRepository.findById(id);
    }
}

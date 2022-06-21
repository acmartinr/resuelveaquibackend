package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Repository.ProductSoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSoldService {

    @Autowired
    private ProductSoldRepository productoSoldRepository;

    public ProductSold create(ProductSold productoSold){
        return productoSoldRepository.save(productoSold);
    }

    public List<ProductSold> getAllProductsSolds(){
        return productoSoldRepository.findAll();
    }

    public ProductSold update(Long id, ProductSold productoSold){
        Optional<ProductSold> entity=productoSoldRepository.findById(id);
        ProductSold p=entity.get();
        p=productoSoldRepository.save(productoSold);
        return p;
    }

    public void delete(Long id){
        productoSoldRepository.deleteById(id);
    }

    public Optional<ProductSold> findByID(Long id){
        return productoSoldRepository.findById(id);
    }
}



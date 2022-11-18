package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Producto create(Producto producto) {
        return productoRepository.save(producto);
    }


    public List<Producto> getAllProducts() {
        return productoRepository.findAll();
    }


   
    public Producto update(Long id, Producto producto) {
        Optional<Producto> entity = productoRepository.findById(id);
        Producto p = entity.get();
        /*p.setDate(producto.getDate());
        p.setImg(producto.getImg());
        p.setThumb(producto.getThumb());
        p.setCategory(producto.getCategory());
        p.setCode(producto.getCode());
        p.setColors(producto.getColors());
        p.setCompany(producto.getCompany());
        p.setDescription(producto.getDescription());
        p.setDetails(producto.getDetails());
        p.setFeatured(producto.getFeatured());
        p.setName(producto.getName());
        p.setPrice(producto.getPrice());
        p.setShipping(producto.isShipping());*/
        producto.setId(id);
        productoRepository.save(producto);
        return p;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    public Optional<Producto> findByID(Long id) {
        return productoRepository.findById(id);
    }

    public Page<Producto> pagination(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    public Page<Producto> paginationSearch(String name, String type, Pageable pageable) {
        return productoRepository.findByProductName(name, type, pageable);
    }
}

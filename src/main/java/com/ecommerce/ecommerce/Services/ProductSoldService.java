package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.ProductSoldRepository;
import com.ecommerce.ecommerce.Repository.ProductoRepository;
import com.ecommerce.ecommerce.common.payload.exception.BussinesRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSoldService {

    @Autowired
    private ProductSoldRepository productoSoldRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ProductoService productoService;

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


    public List<ProductSold> addAllProductSold(ProductSold[] productsSold, User user, Sale saleObject) throws BussinesRuleException {
       List<ProductSold> productsAfterSold = new ArrayList<>();
        for (ProductSold productSold : productsSold) {
            Producto pr = productoRepository.findById(productSold.getId()).get();
            if (pr.getStock() < productSold.getQuantity())
                throw new BussinesRuleException("1026","Cantidad de elementos del tipo: "+pr.getName()+" insuficientes en la tienda");
                //return new ArrayList<>();
            //return ResponseEntity.ok("Solo hay una disponibilidad total de " + pr.getStock() + " para " + pr.getName());
            pr.subtracExistence(productSold.getQuantity());
            productoService.update(pr.getId(), pr);
            ProductSold ps = new ProductSold();
            ps.setShoppingCar(user.getShoppingCar());
            ps.setSale(saleObject);
            ps.setQuantity(productSold.getQuantity());
            ps.setProduct(pr);
            productsAfterSold.add(ps);
            productoSoldRepository.save(ps);
        }
        return productsAfterSold;
    }
}



package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.ShoppingCar;
import com.ecommerce.ecommerce.Repository.ShoppingCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCarService {

    @Autowired
    ShoppingCarRepository shoppingCarRepository;

    public ShoppingCar create(ShoppingCar shoppingCar){
        return shoppingCarRepository.save(shoppingCar);
    }

    public List<ShoppingCar> getAllShoppingCars(){
        return shoppingCarRepository.findAll();
    }

    public ShoppingCar update(Long id, ShoppingCar shoppingCar){
        Optional<ShoppingCar> entity=shoppingCarRepository.findById(id);
        ShoppingCar sc=entity.get();
        shoppingCar.setId(id);
        shoppingCarRepository.save(shoppingCar);
        return sc;
    }

    public void delete(Long id){
        shoppingCarRepository.deleteById(id);
    }
    public ShoppingCar clear(Long id){
        Optional<ShoppingCar> entity=shoppingCarRepository.findById(id);
        ShoppingCar sc=entity.get();
        sc.setProducts("[]");
        shoppingCarRepository.save(sc);
        return sc;
    }
    public Optional<ShoppingCar> findByID(Long id){
        return shoppingCarRepository.findById(id);
    }

}

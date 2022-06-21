package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.ShoppingCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCarRepository extends JpaRepository<ShoppingCar,Long> {
}

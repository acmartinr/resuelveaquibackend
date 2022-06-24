package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}

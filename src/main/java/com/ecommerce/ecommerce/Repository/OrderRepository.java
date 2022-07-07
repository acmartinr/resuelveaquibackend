package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.Order;
import com.ecommerce.ecommerce.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select distinct o from Order o join User u on o.userOrder=:param")
     List<Order> orderByUser(@Param("param")User user);

}

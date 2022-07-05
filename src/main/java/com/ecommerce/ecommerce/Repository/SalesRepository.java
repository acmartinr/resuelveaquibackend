package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sale, Long> {
    @Query("select s from Sale s join User u on s.shopping=:param")
     List<Sale> saleByUser(@Param("param") User user);
}

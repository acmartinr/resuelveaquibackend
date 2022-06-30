package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sale, Long> {
    /*@Query("select x from Sale x where x.shopping_id like: param")
    public abstract List<Sale> shopUser(@Param("param")Long id);*/
}

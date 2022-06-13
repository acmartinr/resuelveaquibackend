package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sale, Long> {
}

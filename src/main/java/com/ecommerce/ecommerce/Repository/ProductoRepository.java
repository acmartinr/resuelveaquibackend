package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    Producto findFirstByCode(String codigo);
    @Query("select distinct p from Producto p where Upper(p.name) like %:param%")
    Page<Producto> findByProductName(@Param("param") String name, Pageable pageable);
}

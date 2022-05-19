package com.ecommerce.ecommerce.Repository;

;
import com.ecommerce.ecommerce.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
}

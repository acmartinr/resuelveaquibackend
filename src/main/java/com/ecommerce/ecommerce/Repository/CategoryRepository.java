package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.Category;
import com.ecommerce.ecommerce.Models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}

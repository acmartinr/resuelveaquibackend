package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Models.Category;
import com.ecommerce.ecommerce.Models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select distinct c from Category c left join Producto p on c.type = p.category where p.shipping = true")
    List<Category> findCategoryByProducts();
}

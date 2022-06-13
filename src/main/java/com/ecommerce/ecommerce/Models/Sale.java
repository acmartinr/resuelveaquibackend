package com.ecommerce.ecommerce.Models;

import com.ecommerce.ecommerce.Utils.DateTimeUtil;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dateAndTime;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private Set<ProductSold> products;

    @ManyToOne
    @JoinColumn(name = "shopping_id", nullable = false)
    private User shopping;

    public Sale() {
        this.dateAndTime = DateTimeUtil.obtenerFechaYHoraActual();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        Double total = 0D;
        for (ProductSold productSold : this.products) {
            total += productSold.getTotal();
        }
        return total;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Set<ProductSold > getProducts() {
        return products;
    }

    public void setProducts(Set<ProductSold > productos) {
        this.products = productos;
    }

    public User getUserShopping() {
        return shopping;
    }

    public void setUserShopping(User user) {
        this.shopping = user;
    }
}

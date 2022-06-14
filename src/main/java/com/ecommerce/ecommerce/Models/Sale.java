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

    private Double amount;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private Set<Producto> products;

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

    public Double getAmount() {
        Double amount = 0D;
        for (Producto producto : this.products) {
            amount += producto.getTotal();
        }
        this.amount=amount;
        return this.amount;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }


    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Set<Producto> getProducts() {
        return products;
    }

    public void setProducts(Set<Producto > productos) {
        this.products = productos;
    }

    public User getUserShopping() {
        return shopping;
    }

    public void setUserShopping(User user) {
        this.shopping = user;
    }
}

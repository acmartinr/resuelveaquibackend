package com.ecommerce.ecommerce.Models;

import javax.persistence.*;

@Entity
public class ProductSold {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer quantity;
    private Double price;
    private String name;
    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    public ProductSold(Integer quantity, Double price, String name, Sale sale) {
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.sale = sale;
    }

    public ProductSold() {
    }

    public Double getTotal() {
        return this.quantity * this.price;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double precio) {
        this.price = precio;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

   }

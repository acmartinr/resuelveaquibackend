package com.ecommerce.ecommerce.Models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class ShoppingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String products = "[]";

    @OneToMany(mappedBy = "shoppingCar", cascade = CascadeType.ALL)
    private Set<ProductSold> productSolds;
    /*
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private Set<ProductSold> product;

     */
    private Integer quantity = 0;
    public ShoppingCar(){
        super();
    }

    public ShoppingCar(int idu){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
/*
    public Set<ProductSold> getProduct() {
        return product;
    }

    public void setProduct(Set<ProductSold> product) {
        this.product = product;
    }


 */

}

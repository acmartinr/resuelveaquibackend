package com.ecommerce.ecommerce.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ShoppingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "user_id", unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private Set<Producto> product;
    private Integer quantity;
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


    public Set<Producto> getProduct() {
        return product;
    }

    public void setProduct(Set<Producto> product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

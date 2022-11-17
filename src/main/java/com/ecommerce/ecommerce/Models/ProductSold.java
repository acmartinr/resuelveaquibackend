package com.ecommerce.ecommerce.Models;

import javax.persistence.*;

@Entity
public class ProductSold {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Producto product;
    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "cod_id")
    private Sale sale;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "shopp_id")
    private ShoppingCar shoppingCar;

    public ProductSold() {
        super();
    }
    public ProductSold(Long id,Integer quantity) {
        this.quantity = quantity;
        this.id = id;
    }



   /* public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }*/


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Producto getProduct() {
        return product;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public ShoppingCar getShoppingCar() {
        return shoppingCar;
    }

    public void setShoppingCar(ShoppingCar shoppingCar) {
        this.shoppingCar = shoppingCar;
    }
}

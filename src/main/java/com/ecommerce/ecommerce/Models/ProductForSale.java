package com.ecommerce.ecommerce.Models;

public class ProductForSale extends Producto {
    private Integer quantity;

    public ProductForSale(String name, Double price,String code, String category, boolean shipping, Integer quantity) {
        super(name,price,code,category,shipping);
        this.quantity = quantity;
    }


    public void increaseQuantity() {
        this.quantity++;
    }

    public Integer getCantidad() {
        return quantity;
    }

    public Double getTotal() {
        return this.getPrice() * this.quantity;
    }
}

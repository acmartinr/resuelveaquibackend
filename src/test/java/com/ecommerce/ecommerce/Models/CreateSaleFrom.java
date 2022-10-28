package com.ecommerce.ecommerce.Models;

import com.ecommerce.ecommerce.payload.request.PaymentRequest;
import com.sun.istack.NotNull;

import javax.validation.constraints.Size;
import java.util.List;

public class CreateSaleFrom {
    @NotNull
    private List<ProductSold> productos;
    @NotNull
    private double amount;
    @NotNull
    private Long user_id;
    @NotNull
    private Order order;
    @NotNull
    private PaymentRequest request;

    public CreateSaleFrom(List<ProductSold> productos, double amount,Long user_id,Order order,PaymentRequest request) {
        this.productos = productos;
        this.amount = amount;
        this.user_id = user_id;
        this.order = order;
        this.request = request;
    }

    public List<ProductSold> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductSold> productos) {
        this.productos = productos;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PaymentRequest getRequest() {
        return request;
    }

    public void setRequest(PaymentRequest request) {
        this.request = request;
    }
}
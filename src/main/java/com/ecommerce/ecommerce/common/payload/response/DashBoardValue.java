package com.ecommerce.ecommerce.common.payload.response;

public class DashBoardValue {
    private Integer sales;
    private Integer users;
    private Integer orders;
    private Integer products;
    public DashBoardValue(Integer sales, Integer users, Integer orders,Integer products) {
        this.sales = sales;
        this.users = users;
        this.orders = orders;
        this.products = products;
    }
    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Integer getProducts() {
        return products;
    }

    public void setProducts(Integer products) {
        this.products = products;
    }
}

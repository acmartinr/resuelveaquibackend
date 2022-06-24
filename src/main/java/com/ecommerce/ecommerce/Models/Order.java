package com.ecommerce.ecommerce.Models;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    @Column(name="dateAndTimes")
    private String dateAndTime;
    @Column(name = "statuses")
    private Integer status;
    @Column(name = "addresses")
    private String address;
    @Column(name = "names")
    private String name;
    @Column(name = "telefs")
    private String telef;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private User userOrder;

    @JoinColumn(name = "orderSale_id", unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private Sale sale;

    public Order(){
        super();
    }

    public Order(String dateAndTime, Integer status, String address, String name, String telef) {
        this.dateAndTime = dateAndTime;
        this.status = status;
        this.address = address;
        this.name = name;
        this.telef = telef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelef() {
        return telef;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }

    public User getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(User userOrder) {
        this.userOrder = userOrder;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
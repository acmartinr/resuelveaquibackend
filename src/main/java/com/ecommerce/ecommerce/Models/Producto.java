package com.ecommerce.ecommerce.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name="producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="price")
    private double price;
    @Column(name="stock")
    private int stock;
    @Column(name = "img")
    private String[] img;

    @Column(name = "thumb")
    private String[] thumb;
    @Column(name = "colors")
    private String[] colors;
    @Column(name="company")
    private String company;
    @Column(name="description")
    private String description;
    @Column(name="details")
    private String details;

    @Column(name="featured")
    private String featured;
    @Column(name = "category")
    private String category;
    @Column(name="shipping")
    private boolean shipping;

    public Producto() {
        super();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {return stock; }

    public void setStock(int stock) {this.stock = stock; }

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public String[] getThumb() {return thumb; }

    public void setThumb(String[] thumb) {this.thumb = thumb; }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {return details; }

    public void setDetails(String details) {this.details = details; }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {this.category = category; }

    public String getFeatured() {return featured; }

    public void setFeatured(String featured) {this.featured = featured; }

    public boolean isShipping() {
        return shipping;
    }

    public void setShipping(boolean shipping) {
        this.shipping = shipping;
    }
}

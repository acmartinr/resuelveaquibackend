package com.ecommerce.ecommerce.Models;

import javax.persistence.*;

@Entity
@Table (name="producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    @Column(name="names")
    private String name;
    @Column(name="prices")
    private Double price;
    @Column(name="stocks")
    private Integer stock;

    @Column(name="codes")
    private String code;
    @Column(name = "images")
    private String img;
    @Column(name = "thumbs")
    private String thumb;
    @Column(name = "colors")
    private String colors;
    @Column(name="companies")
    private String company;
    @Column(name="descriptions")
    private String description;
    @Column(name="details")
    private String details;
    @Column(name="featured")
    private Boolean featured;
    @Column(name = "categories")
    private String category;
    @Column(name="shipping")
    private boolean shipping;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    ShoppingCar shop;


    //private Integer quantity;



    public Producto() {
        super();
    }

    public Producto(String name,Double price,Integer stock,String code,String category, Boolean shipping) {
        this.name=name;
        this.price=price;
        this.stock=stock;
        this.code=code;
        this.category=category;
        this.shipping=shipping;
    }

    /*public Producto(String name,Double price,String code,String category, boolean shipping) {
        this.name=name;
        this.price=price;
        this.code=code;
        this.category=category;
        this.shipping=shipping;
    }

    public Producto(String name, Double price,String code, String category, boolean shipping, Integer quantity) {
        this.quantity = quantity;
    }*/

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {return this.stock; }

    public boolean noExistence() {
        return this.stock <= 0;
    }

    public void setStock(int stock) {this.stock = stock; }

    public void subtracExistence(int stock) {
        this.stock -= stock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumb() {return thumb; }

    public void setThumb(String thumb) {this.thumb = thumb; }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
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

    public Boolean getFeatured() {return featured; }

    public void setFeatured(boolean featured) {this.featured = featured; }

    public boolean isShipping() {
        return shipping;
    }

    public void setShipping(boolean shipping) {
        this.shipping = shipping;
    }

   /* public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return this.quantity * this.price;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public Integer getCantidad() {
        return quantity;
    }*/
}

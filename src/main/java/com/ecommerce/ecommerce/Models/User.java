package com.ecommerce.ecommerce.Models;

import com.ecommerce.ecommerce.Security.jwt.JwtUtils;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;

    @Column(name="firstnames")
    private String firstname;
    @Column(name="lastnames")
    private String lastname;
    @Column(name="usernames")
    private String username;

    @Column(name="passwords")
    private String password;

    @Column(name="emails")
    private String email;

    @Column(name="addresses")
    private String address;

    @Column(name="rols")
    private String rol;
    private String token;

    @JoinColumn(name = "shopCar_id", unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCar shoppingCar;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private Set<Order> orders;

    public User(){
        super();
    }
    public User(String firstname, String lastname,String address, String username, String email, String password,String rol){
        this.firstname=firstname;
        this.lastname=lastname;
        this.address=address;
        this.username=username;
        this.email=email;
        this.password=password;
        this.rol = rol;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ShoppingCar getShoppingCar() {
        return shoppingCar;
    }

    public void setShoppingCar(ShoppingCar shoppingCar) {
        this.shoppingCar = shoppingCar;
    }

    /* public Set<Sale> getSale() {
        return sales;
    }

    public void setSale(Set<Sale> sales) {
        this.sales = sales;
    }*/
}

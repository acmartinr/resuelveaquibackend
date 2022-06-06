package com.ecommerce.ecommerce.Models;

import javax.persistence.*;

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

    @Column(name="rols")
    private String rol;

    private String token;

    public User(){
        super();
    }
    public User(String firstname, String lastname, String username, String email, String password){
        this.firstname=firstname;
        this.lastname=lastname;
        this.username=username;
        this.email=email;
        this.password=password;
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




}

package Models;

import javax.persistence.*;

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
    @Column(name = "img")
    private String img;
    @Column(name = "colors")
    private String [] colors;
    @Column(name="company")
    private String company;
    @Column(name="description")
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name="shipping")
    private boolean shipping;


    public Producto(Long id, String name, double price, String img, String[] colors, String company, String description, String category, boolean shipping) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
        this.colors = colors;
        this.company = company;
        this.description = description;
        this.category = category;
        this.shipping = shipping;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isShipping() {
        return shipping;
    }

    public void setShipping(boolean shipping) {
        this.shipping = shipping;
    }
}

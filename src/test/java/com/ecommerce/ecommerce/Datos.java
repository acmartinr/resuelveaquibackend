package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Models.User;

public class Datos {
    public static final User testUser = new User(1L,"Alberto","Martin","Reparto num 11","acmartin", "albertocarlosmartin@gmail.com", "12345678","ROLE_ADMIN");
    public static final Producto productTest1 = new Producto(1L,"laptop",100.00,100,"1234","electronicos",true);
    public static final Producto productTest2 = new Producto(2L,"smartphone",200.00,50,"1034","electronicos",false);
    public static final Producto productTest3 = new Producto(3L,"camisa",100.00,2,"1244","ropa",true);
    public static final Producto productTest4 = new Producto(4L,"silla",100.00,900,"1234","hogar",false);

    public static final ProductSold productSoldTest1 = new ProductSold(1L,10);
    public static final ProductSold productSoldTest2 = new ProductSold(2L,30);
    public static final ProductSold productSoldTest3 = new ProductSold(3L,12);
    public static final ProductSold productSoldTest4 = new ProductSold(4L,8);

    public static final Sale sale = new Sale();
    public static final Sale saleObjectTest = new Sale();
}

package com.ecommerce.ecommerce.Services;


import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.SalesRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.Utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SaleService {
    @Autowired
    SalesRepository salesRepository;
    @Autowired
    UserRepository userRepository;

    public List<Sale> findAll(){
        return salesRepository.findAll();
    }
    public List<Sale> saleByUser(User user){
        return salesRepository.saleByUser(user);
    }
    public Optional<Sale> findById(Long user){
        return salesRepository.findById(user);
    }

    public Sale createSale(ProductSold[] productos, double amount, Long user_id) {
        Sale sale = new Sale();
        User user = userRepository.findById(user_id).get();
        Set<ProductSold> products = new HashSet<>(Arrays.asList(productos));
        sale.setAmount(amount);
        sale.setProductsSolds(products);
        sale.setUserShopping(user);
        String time = DateTimeUtil.obtenerFechaYHoraActual();
        sale.setDateAndTime(time);
        return salesRepository.save(sale);
    }

}

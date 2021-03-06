package com.ecommerce.ecommerce.Controller;


import com.ecommerce.ecommerce.Models.Order;
import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.OrderRepository;
import com.ecommerce.ecommerce.Repository.ProductoRepository;
import com.ecommerce.ecommerce.Repository.SalesRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.payload.response.DashBoardValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private SalesRepository salesRepository;

    @GetMapping(value = "/getDashboardValues")
    public ResponseEntity<DashBoardValue> getDashboardValues() {
        DashBoardValue dv = new DashBoardValue(salesRepository.findAll().size(), userRepository.findAll().size(), orderRepository.findAll().size(), productoRepository.findAll().size());
        return new ResponseEntity<DashBoardValue>(dv, HttpStatus.OK);
    }
}

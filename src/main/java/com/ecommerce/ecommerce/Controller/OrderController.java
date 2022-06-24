package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.Order;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.SalesRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.Services.OrderService;
import com.ecommerce.ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private SalesRepository salesRepository;

    @CrossOrigin
    @GetMapping("/")
    private ResponseEntity<List<Order>> list(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    @CrossOrigin
    @RequestMapping(value="/createOrder")
    private ResponseEntity<String> create(@RequestPart Order order, @RequestPart Long idu,
                                          @RequestPart Long ids) throws IOException {
        order.setUserOrder(userRepository.findById(idu).get());
        order.setSale(salesRepository.findById(ids).get());
        orderService.create(order);
        return ResponseEntity.ok("Orden registrada correctamente");
    }

    @CrossOrigin
    @GetMapping(value = "getOrder/{id}")
    private ResponseEntity<Optional<Order>> buscar(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.findByID(id));
    }

    @CrossOrigin
    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Order order){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.update(id,order));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(orderService.findByID(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
            orderService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
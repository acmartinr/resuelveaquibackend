package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.Order;
import com.ecommerce.ecommerce.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order create(Order order){
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order update(Long id, Order order){
        Optional<Order> entity=orderRepository.findById(id);
        Order o=entity.get();
        o=orderRepository.save(order);
        return o;
    }

    public void delete(Long id){
        orderRepository.deleteById(id);
    }

    public Optional<Order> findByID(Long id){
        return orderRepository.findById(id);
    }
}

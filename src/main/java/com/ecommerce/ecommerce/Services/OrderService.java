package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.Order;
import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.OrderRepository;
import com.ecommerce.ecommerce.Security.services.PdfGenerateService;
import com.ecommerce.ecommerce.Utils.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PdfGenerateService pdfGenerateService;

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order update(Long id, Order order) {
        Optional<Order> entity = orderRepository.findById(id);
        Order o = entity.get();
        o = orderRepository.save(order);
        return o;
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public void generatePdf(Order order, Sale saleObject, List<ProductSold> productsAfterSold, double amount, User user) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("productSold", productsAfterSold);
            data.put("order", order);
            data.put("user", user);
            data.put("amount", amount);

            String pdf=java.util.UUID.randomUUID().toString();
            order.setUserOrder(user);
            order.setSale(saleObject);
            order.setDateAndTime(saleObject.getDateAndTime());
            order.setBill_payment(pdf+".pdf");
            pdfGenerateService.generatePdfFile("pdf", data, pdf+".pdf");
            orderRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Order> findByID(Long id) {
        return orderRepository.findById(id);
    }
}

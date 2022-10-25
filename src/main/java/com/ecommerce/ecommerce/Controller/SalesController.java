package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.*;
import com.ecommerce.ecommerce.Repository.*;
import com.ecommerce.ecommerce.Services.PaymentService;
import com.ecommerce.ecommerce.Services.ProductoService;
import com.ecommerce.ecommerce.Utils.DateTimeUtil;
import com.ecommerce.ecommerce.Utils.PDFGenerator;
import com.ecommerce.ecommerce.payload.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@CrossOrigin
@Controller
@RequestMapping(path = "/api/sales")
public class SalesController {
    @Autowired
    SalesRepository salesRepository;
    @Autowired
    ProductoService productoService;
    @Autowired
    ProductSoldRepository productSoldRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShoppingCarRepository shoppingCarRepository;
    @Autowired
    PaymentService service;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping(value = "/")
    public ResponseEntity<List<Sale>> showSales() {
        List<Sale> sales = salesRepository.findAll();
        return ResponseEntity.ok(sales);
    }

    /*@RequestPart PaymentRequest request @RequestPart ProductSold[] productos,@RequestPart double amount,*/
    @PostMapping(value = "/create_sale")/*,@RequestPart Order order,
    @RequestPart PaymentRequest request*/
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public ResponseEntity<String> createSale(@RequestPart ProductSold[] productos, @RequestPart double amount,
                                             @RequestPart Long user_id, @RequestPart Order order,
                                             @RequestPart PaymentRequest request) throws Exception {
        Sale s = new Sale();
        Sale sale = new Sale();
        User user = userRepository.findById(user_id).get();
        Set<ProductSold> products = new HashSet<>(Arrays.asList(productos));
        Set<ProductSold> productsAfterSold = new HashSet<>();
        sale.setAmount(amount);
        sale.setProductsSolds(products);
        sale.setUserShopping(user);
        String time = DateTimeUtil.obtenerFechaYHoraActual();
        sale.setDateAndTime(time);

        s = salesRepository.save(sale);
        for (ProductSold productSold : productos) {
            Producto pr = productoRepository.findById(productSold.getId()).get();
            if (pr.getStock() < productSold.getQuantity())
                return ResponseEntity.ok("Solo hay una disponibilidad total de " + pr.getStock() + " para " + pr.getName());
            pr.subtracExistence(productSold.getQuantity());
            productoService.update(pr.getId(), pr);
            // productSold.setSale(s);
            //productSold.setProduct(pr);
            ProductSold ps = new ProductSold();
            ps.setShoppingCar(user.getShoppingCar());
            ps.setSale(s);
            ps.setQuantity(productSold.getQuantity());
            ps.setProduct(pr);
            productsAfterSold.add(ps);
            productSoldRepository.save(ps);
        }

        String chargeId = service.charge(request);
        if (chargeId != null) {
            try {
                String pdf = PDFGenerator.generatePDF(s, productsAfterSold, amount);
                order.setUserOrder(user);
                order.setSale(s);
                order.setDateAndTime(time);
                order.setBill_payment(pdf);
                orderRepository.save(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new ResponseEntity<String>(chargeId, HttpStatus.OK);
        } else
            return new ResponseEntity<String>("Please check the credit card details entered", HttpStatus.BAD_REQUEST);
       /*return chargeId!=null? new ResponseEntity<String>(chargeId, HttpStatus.OK):
                new ResponseEntity<String>("Please check the credit card details entered",HttpStatus.BAD_REQUEST);*/
        // return ResponseEntity.ok("Venta realizada correctamente");
    }

    /*@PostMapping(value="/payment")
    public ResponseEntity<String> completePayment(@RequestBody PaymentRequest request) throws StripeException {
        String chargeId= service.charge(request);
        return chargeId!=null? new ResponseEntity<String>(chargeId, HttpStatus.OK):
                new ResponseEntity<String>("Please check the credit card details entered",HttpStatus.BAD_REQUEST);
    }*/

    @GetMapping(value = "/getAll_sale")
    public ResponseEntity<List<Sale>> getAllSale() throws IOException {
        return ResponseEntity.ok(salesRepository.findAll());
    }



   /* @GetMapping(value = "/getShopUser")
    public List<Sale> getShopUser(@RequestPart Long id) {
        List<Sale> ls=new ArrayList<>();
        //ls=salesRepository.shopUser(id);
        List<Sale> sales = salesRepository.findAll();
        for(Sale sale:sales){
            if (sale.getUserShopping().getId()==id)
                ls.add(sale);
        }
        return ls;
    }*/

    @GetMapping(value = "/getShopUser/{id}")
    public ResponseEntity<List<Sale>> getShopUser(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        return ResponseEntity.ok(salesRepository.saleByUser(user));
    }


}

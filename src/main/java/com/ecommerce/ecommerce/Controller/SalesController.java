package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.*;
import com.ecommerce.ecommerce.Repository.*;
import com.ecommerce.ecommerce.Services.*;
import com.ecommerce.ecommerce.Utils.Constants;
import com.ecommerce.ecommerce.common.payload.exception.CreditCardException;
import com.ecommerce.ecommerce.common.payload.request.PaymentRequest;
import com.ecommerce.ecommerce.common.payload.response.ErrorResponse;
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
    SaleService saleService;
    @Autowired
    ProductSoldService productSoldService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentService paymentService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    private EmailServiceImpl emailService;


    @GetMapping(value = "/")
    public ResponseEntity<List<Sale>> showSales() {
        List<Sale> sales = saleService.findAll();
        return ResponseEntity.ok(sales);
    }


    @PostMapping(value = "/create_sale")
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEntity<Object> createSale(@RequestPart ProductSold[] productos, @RequestPart double amount,
                                             @RequestPart Long user_id, @RequestPart Order order,
                                             @RequestPart PaymentRequest request) throws Exception {
        User user = userService.findByID(user_id).get();
        Sale saleObject = saleService.createSale(productos, amount, user_id);
        List<ProductSold> productsAfterSold = productSoldService.addAllProductSold(productos, user, saleObject);
        if (productsAfterSold.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse(Constants.INSUFFICIENT_PRODUCT_STOCK), HttpStatus.BAD_REQUEST);
        } else {
            String chargeId = paymentService.processPayment(request.getAmount(), request.getCurrency(), request.getToken().getId());
            if (chargeId != null) {
                emailService.sendSimpleMessage("info@resuelveaqui.com", "Hola , el usuario " + user.getFirstname() + " " + user.getLastname() + " ha realizado una compra.", "Compra realizada, revise en el admin para más detalles");
                emailService.sendSimpleMessage(user.getEmail(), "Orden realizada", "Hola , gracias por comprar en Resuelveaqui, le informamos que su pedido se ha realizado correctamente, puede consultarlo en la opción 'Mis pedidos'");
                orderService.generatePdf(order, saleObject, productsAfterSold, amount, user);
                return new ResponseEntity<>(chargeId, HttpStatus.OK);
            } else {
                throw new CreditCardException("1100", "Por favor , revise los detalles de la tarjeta de credito");
            }
        }
    }


    @GetMapping(value = "/getAll_sale")
    public ResponseEntity<List<Sale>> getAllSale() throws IOException {
        return ResponseEntity.ok(saleService.findAll());
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
        return ResponseEntity.ok(saleService.saleByUser(user));
    }


}

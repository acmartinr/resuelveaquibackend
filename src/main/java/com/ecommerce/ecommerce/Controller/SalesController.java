package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.*;
import com.ecommerce.ecommerce.Repository.*;
import com.ecommerce.ecommerce.Security.services.PdfGenerateService;
import com.ecommerce.ecommerce.Services.*;
import com.ecommerce.ecommerce.Utils.Constants;
import com.ecommerce.ecommerce.Utils.DateTimeUtil;
import com.ecommerce.ecommerce.Utils.PDFGenerator;
import com.ecommerce.ecommerce.payload.request.PaymentRequest;
import com.ecommerce.ecommerce.payload.response.ErrorResponse;
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
    ProductoService productoService;
    @Autowired
    ProductSoldService productSoldService;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShoppingCarRepository shoppingCarRepository;
    @Autowired
    PaymentService paymentService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @Autowired
    private PdfGenerateService pdfGenerateService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Sale>> showSales() {
        List<Sale> sales = saleService.findAll();
        return ResponseEntity.ok(sales);
    }


    @PostMapping(value = "/create_sale")
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> createSale(@RequestPart ProductSold[] productos, @RequestPart double amount,
                                             @RequestPart Long user_id, @RequestPart Order order,
                                             @RequestPart PaymentRequest request) throws Exception {
        User user = userService.findByID(user_id).get();
        Sale saleObject = saleService.createSale(productos, amount, user_id);
        List<ProductSold> productsAfterSold = productSoldService.addAllProductSold(productos, user, saleObject);
        if (productsAfterSold.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse(Constants.INSUFFICIENT_PRODUCT_STOCK), HttpStatus.BAD_REQUEST);
        } else {
            String chargeId = paymentService.processPayment(request.getAmount(),request.getCurrency(),request.getToken().getId());
            if (chargeId != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("productSold", productsAfterSold);
                data.put("order", order);
                data.put("user", user);
                data.put("amount", amount);
                String pdf=java.util.UUID.randomUUID().toString();
                order.setBill_payment(pdf+".pdf");
                pdfGenerateService.generatePdfFile("pdf", data, pdf+".pdf");
                //orderService.generatePdf(order, saleObject, productsAfterSold, amount, user);
                return new ResponseEntity<>(chargeId, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ErrorResponse(Constants.INCORRECT_CARD_CODE), HttpStatus.BAD_REQUEST);
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

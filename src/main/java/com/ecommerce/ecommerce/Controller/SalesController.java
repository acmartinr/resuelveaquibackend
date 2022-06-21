package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.*;
import com.ecommerce.ecommerce.Repository.*;
import com.ecommerce.ecommerce.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping(value = "/")
    public ResponseEntity<List<Sale>> showSales() {
        List<Sale> sales = salesRepository.findAll();
        return ResponseEntity.ok(sales);
    }

    @PostMapping(value="/create_sale")
    public ResponseEntity<String> createSale(@RequestPart ProductSold[] productos,@RequestPart double amount,
                                             @RequestPart Long id) throws IOException {
        Sale s=new Sale();
        Sale sale=new Sale();
        Set<ProductSold> products=new HashSet<>(Arrays.asList(productos));
        sale.setAmount(amount);
        //sale.setProductsSolds(products);
        sale.setUserShopping(userRepository.findById(id).get());
        s=salesRepository.save(sale);
        for (ProductSold productSold:productos){
            Producto pr=productoRepository.findById(productSold.getId()).get();
            if(pr.getStock()< productSold.getQuantity())
                return ResponseEntity.ok("Solo hay una disponibilidad total de "+pr.getStock()+"para"+pr.getName());
            pr.subtracExistence(productSold.getQuantity());
            productoService.update(pr.getId(),pr);
            productSold.setSale(s);
            productSold.setProduct(pr);
            productSoldRepository.save(productSold);
        }
        return ResponseEntity.ok("Venta realizada correctamente");
    }

    @GetMapping(value="/getAll_sale")
    public ResponseEntity<List<Sale>> getAllSale() throws IOException {
        return ResponseEntity.ok(salesRepository.findAll());
    }

    @PostMapping(value = "/addToCar")
    public ResponseEntity<String> agregarAlCarrito(@RequestPart Producto producto,@RequestPart Long id,
                                                   @RequestPart int cant) {
        Set<Producto> prod=new HashSet<>();
        prod.add(producto);
        ShoppingCar shoppingCar = new ShoppingCar();
        Producto productoBuscado = productoRepository.findById(producto.getId()).get();
        if (productoBuscado==null){
            return ResponseEntity.ok("El producto no existe");
        }
        shoppingCar.setProduct(prod);
        shoppingCar.setQuantity(cant);
        shoppingCar.setUser(userRepository.getById(id));
        shoppingCarRepository.save(shoppingCar);
        return ResponseEntity.ok("Producto agregado al carrito correctamente" );
    }

    @GetMapping(value = "/limpiar")
    public ResponseEntity<ShoppingCar> limpiarCarrito(@RequestPart Long id) {
        ShoppingCar shoppingCar = new ShoppingCar();
        shoppingCar.setUser(userRepository.getById(id));
        return ResponseEntity.ok(shoppingCar);
    }
}

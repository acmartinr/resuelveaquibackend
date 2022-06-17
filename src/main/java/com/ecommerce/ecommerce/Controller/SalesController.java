package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.ProductSoldRepository;
import com.ecommerce.ecommerce.Repository.ProductoRepository;
import com.ecommerce.ecommerce.Repository.SalesRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
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

    private List<Producto> obtenerCarrito() {
        List<Producto> carrito = new ArrayList<Producto>();
        return carrito;
    }

    @PostMapping(value = "/agregar")
    public ResponseEntity<String> agregarAlCarrito(@RequestPart Producto producto) {
        List<Producto> carrito = new ArrayList<Producto>();
        Producto productoBuscadoPorCodigo = productoRepository.findFirstByCode(producto.getCode());
        if (productoBuscadoPorCodigo == null) {
            return ResponseEntity.ok("El producto con el código " + producto.getCode() + " no existe");
        }

        return ResponseEntity.ok("" );
    }
}

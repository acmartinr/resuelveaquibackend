package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.ProductForSale;
import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Repository.ProductSoldRepository;
import com.ecommerce.ecommerce.Repository.ProductoRepository;
import com.ecommerce.ecommerce.Repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping(path = "/vender")
public class ToSaleController {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private SalesRepository ventasRepository;
    @Autowired
    private ProductSoldRepository productosVendidosRepository;



    private void cancelShopping(HttpServletRequest request) {
        this.saveShopping(new ArrayList<>(), request);
    }

    @GetMapping(value = "/limpiar")
    public ResponseEntity<String> cancelarCompra(HttpServletRequest request) {
        this.cancelShopping(request);
         return ResponseEntity.ok("Compra cancelada");
    }

    @PostMapping(value = "/terminar")
    public ResponseEntity<String> completedSale(HttpServletRequest request) {
        ArrayList<ProductForSale> carrito = this.getShopping(request);
        // Si no hay carrito o está vacío, regresamos inmediatamente
        if (carrito == null || carrito.size() <= 0) {
            return ResponseEntity.ok("Su carrito esta vacio");
        }
        Sale v = ventasRepository.save(new Sale());
        // Recorrer el carrito
        for (ProductForSale productoParaVender : carrito) {
            // Obtener el producto fresco desde la base de datos
            Producto p = productoRepository.findById(productoParaVender.getId()).orElse(null);
            if (p == null) continue; // Si es nulo o no existe, ignoramos el siguiente código con continue
            // Le restamos existencia
            p.subtracExistence(productoParaVender.getCantidad());
            // Lo guardamos con la existencia ya restada
            productoRepository.save(p);
            // Creamos un nuevo producto que será el que se guarda junto con la venta
            ProductSold productoVendido = new ProductSold(productoParaVender.getCantidad(), productoParaVender.getPrice(), productoParaVender.getName(), v);
            // Y lo guardamos
            productosVendidosRepository.save(productoVendido);
        }

        // Al final limpiamos el carrito
        this.cancelShopping(request);
        // e indicamos una venta exitosa
        return ResponseEntity.ok("La compra se realizo satisfactoriamente");
    }



    private ArrayList<ProductForSale> getShopping(HttpServletRequest request) {
        ArrayList<ProductForSale> shopping = (ArrayList<ProductForSale>) request.getSession();
        if (shopping == null) {
            shopping = new ArrayList<>();
        }
        return shopping;
    }

    private void saveShopping(ArrayList<ProductForSale> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
    }

    @PostMapping(value = "/agregar")
    public ResponseEntity<String> addToShoppingCar(@RequestBody Producto producto, HttpServletRequest request) {
        ArrayList<ProductForSale> carrito = this.getShopping(request);
        Producto productoBuscadoPorCodigo = productoRepository.findFirstByCode(producto.getCode());
        if (productoBuscadoPorCodigo == null) {
            return ResponseEntity.ok("El producto con el codigo "+producto.getCode()+" no existe");
        }
        if (productoBuscadoPorCodigo.noExistence()) {
            return ResponseEntity.ok("El producto está agotado");
        }
        boolean encontrado = false;
        for (ProductForSale productoParaVenderActual : carrito) {
            if (productoParaVenderActual.getCode().equals(productoBuscadoPorCodigo.getCode())) {
                productoParaVenderActual.increaseQuantity();
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            carrito.add(new ProductForSale(productoBuscadoPorCodigo.getName(), productoBuscadoPorCodigo.getPrice(),
                    productoBuscadoPorCodigo.getCode(), productoBuscadoPorCodigo.getCategory(),
                    productoBuscadoPorCodigo.isShipping(),4 ));
        }
        this.saveShopping(carrito, request);
        return ResponseEntity.ok("El producto se agrego correctamente");
    }




}

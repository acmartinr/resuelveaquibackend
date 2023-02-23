package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.ShoppingCar;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Services.ProductSoldService;
import com.ecommerce.ecommerce.Services.ProductoService;
import com.ecommerce.ecommerce.Services.ShoppingCarService;
import com.ecommerce.ecommerce.Services.UserService;
import com.ecommerce.ecommerce.common.payload.request.Cart;
import com.google.gson.Gson;
import com.stripe.model.Product;
import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/shoppingCart")
public class ShoppingCarController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    ProductSoldService productSoldService;
    @Autowired
    ShoppingCarService shoppingCarService;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<ShoppingCar>> getAllProducts() {
        List<ShoppingCar> list = shoppingCarService.getAllShoppingCars();
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/createShoppingCar")
    private ResponseEntity<ShoppingCar> create(@RequestBody ShoppingCar shoppingCar) throws IOException {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(shoppingCarService.create(shoppingCar));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping(value = "/clearcart/{userid}")
    public ResponseEntity<ShoppingCar> clear(@PathVariable("userid") Long userid) throws IOException {
        try {
            System.out.println("Deleting"+userid);
            long cartId = userService.findByID(userid).get().getShoppingCar().getId();
            System.out.println("cartId"+cartId);
            return ResponseEntity.status(HttpStatus.OK).body(shoppingCarService.clear(cartId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    @DeleteMapping(value = "/removeFromCart/{userid}/{id}")
    public ResponseEntity<String> removeAlCarrito(@PathVariable("id") Long id, @PathVariable("userid") Long userid) {
        Gson gson = new Gson();
        long cartId = userService.findByID(userid).get().getShoppingCar().getId();
        ShoppingCar sc = shoppingCarService.findByID(cartId).get();
        List<Cart> products = gson.fromJson(sc.getProducts(), List.class);


        ArrayList<Cart> al = new ArrayList<>();
        for (int i = 0;i< products.size(); i++) {
            Map<String, Any> obj = new HashMap<String,Any>((Map<? extends String, ? extends Any>) products.get(i));
            String productId = String.valueOf(obj.get("id"));
            if (Float.parseFloat(productId) != Float.parseFloat(String.valueOf(id))) {
                al.add(products.get(i));
           }
        }
        String newProducts = gson.toJson(al);
        sc.setProducts(newProducts);
        shoppingCarService.update(sc.getId(), sc);
        return ResponseEntity.ok("El producto fue extraido del carrito");
    }

    @PostMapping(value = "/addToCar/{userid}")
    public ResponseEntity<String> agregarAlCarrito(@RequestPart Cart cart, @PathVariable("userid") Long userid) {
        System.out.println("adding to cart");
        Gson gson = new Gson();
        try {
            List<Cart> a= new ArrayList<>();
           // String cartValue = gson.toJson(cart).toString();
            long cartId = userService.findByID(userid).get().getShoppingCar().getId();
            ShoppingCar sc = shoppingCarService.findByID(cartId).get();
            Producto productoBuscado = productoService.findByID(cart.getId()).get();
            //String p= gson.toJson(sc.getProducts());
            //return ResponseEntity.ok(p);

            if (productoBuscado.isShipping()) {
                //a.add(cart);
              //  List<Cart> lc = gson.fromJson(p, List.class);
               // return ResponseEntity.ok("Producto agregado al carrito correctamente");

                a.add(cart);
                String newProducts = gson.toJson(a);
               // return ResponseEntity.ok(newProducts);
                sc.setProducts(newProducts);
                shoppingCarService.update(sc.getId(), sc);
                return ResponseEntity.ok("Producto agregado al carrito correctamente");
            } else {
                return ResponseEntity.ok("El producto no esta disponible");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }





        /*
        Set<Producto> prod=new HashSet<>();
        prod.add(producto);
        ShoppingCar shoppingCar = new ShoppingCar();
        //Producto productoBuscado = productoRepository.findById(producto.getId()).get();
        if (productoBuscado==null){
            return ResponseEntity.ok("El producto no existe");
        }
        shoppingCar.setProduct(prod);
        shoppingCar.setQuantity(cant);
        //shoppingCarRepository.save(shoppingCar);

         */

    }

    //@PostMapping(value = "/addToCar/{userid}")
    public ResponseEntity<String> newAddCarrito(@RequestParam Product product, @RequestParam @PathVariable("userid") Long userid) {


        return (ResponseEntity<String>) ResponseEntity.badRequest();
    }




        @GetMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Producto product) {

        return ResponseEntity.ok("200");
    }

    @GetMapping(value = "get/user/{userid}")
    private ResponseEntity<Optional<ShoppingCar>> getByUser(@PathVariable("userid") Long userid) {
        User u = userService.findByID(userid).get();
        return ResponseEntity.ok(shoppingCarService.findByID(u.getShoppingCar().getId()));
    }

    @GetMapping(value = "get/{id}")
    private ResponseEntity<Optional<ShoppingCar>> buscar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(shoppingCarService.findByID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ShoppingCar shoppingCar) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(shoppingCarService.update(id, shoppingCar));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        if (shoppingCarService.findByID(id) == null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
            shoppingCarService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PostMapping("/quantity/{userid}/{id}")
    public ResponseEntity<String> setQuantityProduct(@PathVariable("userid") Long userid,@PathVariable("id") Long id,
                                                       @RequestBody Integer cant){
        Gson gson = new Gson();
        long cartId = userService.findByID(userid).get().getShoppingCar().getId();
        ShoppingCar sc = shoppingCarService.findByID(cartId).get();
        List<Cart> products = gson.fromJson(sc.getProducts(), List.class);


        ArrayList<Cart> al = new ArrayList<>();
        for (int i = 0;i< products.size(); i++) {
            Map<String, Any> obj = new HashMap<String,Any>((Map<? extends String, ? extends Any>) products.get(i));
            String productId = String.valueOf(obj.get("id"));
            if(Float.parseFloat(productId) != Float.parseFloat(String.valueOf(id)))
                al.add(products.get(i));
            else {
                String.valueOf(obj.get("max")).replace(String.valueOf(obj.get("max")),cant.toString());
                al.add(products.get(i));
            }
        }
        String newProducts = gson.toJson(al).toString();
        sc.setProducts(newProducts);
        shoppingCarService.update(sc.getId(), sc);
        return ResponseEntity.ok("La cantidad del producto fue actualizada");
    }

    @PostMapping(value = "/cleanKart/{userid}")
    public ResponseEntity<ShoppingCar> cleanKart(@PathVariable("userid") Long id){
        Gson gson = new Gson();
        long cartId = userService.findByID(id).get().getShoppingCar().getId();
        ShoppingCar sc = shoppingCarService.findByID(cartId).get();
        ArrayList<Cart> empty = new ArrayList<>();
        String emptyProducts = gson.toJson(empty).toString();
        sc.setProducts(emptyProducts);
        shoppingCarService.update(sc.getId(), sc);
        return ResponseEntity.status(HttpStatus.OK).body(sc);

    }

}

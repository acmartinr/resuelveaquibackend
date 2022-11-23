package com.ecommerce.ecommerce.Services;


import com.ecommerce.ecommerce.Datos;
import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.ProductSoldRepository;
import com.ecommerce.ecommerce.Repository.ProductoRepository;
import com.ecommerce.ecommerce.Repository.SalesRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.common.payload.exception.BussinesRuleException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;


@SpringBootTest
public class ProductSoldServiceUTest {

    @MockBean
    private ProductoRepository productoRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ProductSoldRepository productoSoldRepository;
    @MockBean
    private SalesRepository salesRepository;


    @Autowired
    private ProductSoldService productSoldService;
    @Autowired
    SaleService saleService;

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void should_add_all_products_to_products_sold_error_less_products_than_sold() throws Exception {
        ProductSold productos[] = new ProductSold[4];
        productos[0] = Datos.productSoldTest1;
        productos[1] = Datos.productSoldTest3;
        productos[2] = Datos.productSoldTest4;
        User user = Datos.testUser;
        Sale sale = new Sale();
        given(productoRepository.findById(1L)).willReturn(java.util.Optional.of(Datos.productTest1));
        given(productoRepository.findById(3L)).willReturn(java.util.Optional.of(Datos.productTest3));
        given(productoRepository.findById(4L)).willReturn(java.util.Optional.of(Datos.productTest4));
        given(productoSoldRepository.save(ArgumentMatchers.any())).willReturn(Datos.productSoldTest1);
        //Comprobar que hayan productos suficientes y que esten disponibles en shipping
        Exception exception = assertThrows(BussinesRuleException.class, () -> {
            List<ProductSold> productsAfterSold = productSoldService.addAllProductSold(productos, user, sale);
        });
        String exceptionTxt = "Cantidad de elementos del tipo: "+Datos.productTest3.getName()+" insuficientes en la tienda";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(exceptionTxt));
    }


    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void should_rise_exception_not_shipping_product() throws Exception {
        ProductSold productos[] = new ProductSold[4];
        productos[0] = Datos.productSoldTest1;
        productos[1] = Datos.productSoldTest2;
        productos[2] = Datos.productSoldTest3;
        productos[3] = Datos.productSoldTest4;
        User user = Datos.testUser;
        Sale sale = new Sale();
        given(productoRepository.findById(1L)).willReturn(java.util.Optional.of(Datos.productTest1));
        given(productoRepository.findById(2L)).willReturn(java.util.Optional.of(Datos.productTest2));
        given(productoRepository.findById(3L)).willReturn(java.util.Optional.of(Datos.productTest3));
        given(productoRepository.findById(4L)).willReturn(java.util.Optional.of(Datos.productTest4));
        given(productoSoldRepository.save(ArgumentMatchers.any())).willReturn(Datos.productSoldTest1);
        //Comprobar que hayan productos suficientes y que esten disponibles en shipping
        Exception exception = assertThrows(BussinesRuleException.class, () -> {
            List<ProductSold> productsAfterSold = productSoldService.addAllProductSold(productos, user, sale);
        });
        String exceptionTxt = "Cantidad de elementos del tipo: "+Datos.productTest2.getName()+" insuficientes en la tienda";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(exceptionTxt));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void should_add_all_products_to_products_sold() throws Exception {
        ProductSold productos[] = new ProductSold[3];
        productos[0] = Datos.productSoldTest1;
        productos[1] = Datos.productSoldTest2;
        productos[2] = Datos.productSoldTest4;
        User user = Datos.testUser;
        Sale sale = new Sale();
        given(productoRepository.findById(1L)).willReturn(java.util.Optional.of(Datos.productTest1));
        given(productoRepository.findById(2L)).willReturn(java.util.Optional.of(Datos.productTest2));
        given(productoRepository.findById(4L)).willReturn(java.util.Optional.of(Datos.productTest4));
        given(productoSoldRepository.save(ArgumentMatchers.any())).willReturn(Datos.productSoldTest1);
        List<ProductSold> productsAfterSold = productSoldService.addAllProductSold(productos, user, sale);
        System.out.println(productsAfterSold.size());
        assertThat(productsAfterSold.get(0).getProduct().getStock(), is(90));
        assertThat(productsAfterSold.get(1).getProduct().getStock(), is(20));
        assertThat(productsAfterSold.get(2).getProduct().getStock(), is(892));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void should_create_sale() throws Exception {
        double saleAmount = 100;
        ProductSold productos[] = new ProductSold[3];
        productos[0] = Datos.productSoldTest1;
        productos[1] = Datos.productSoldTest2;
        productos[2] = Datos.productSoldTest4;
        given(userRepository.findById(1L)).willReturn(java.util.Optional.of(Datos.testUser));
        given(salesRepository.save(ArgumentMatchers.any())).willReturn(Datos.sale);
        Sale saleObject = saleService.createSale(productos, saleAmount, 1L);
        assertThat(saleObject.getAmount(), is(saleAmount));
        assertThat(saleObject.getProducts().size(), is(3));
        assertThat(saleObject.getUserShopping().getId(), is(1L));
    }

}

package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.*;
import com.ecommerce.ecommerce.Repository.*;
import com.ecommerce.ecommerce.Security.jwt.AuthEntryPointJwt;
import com.ecommerce.ecommerce.Security.jwt.JwtUtils;
import com.ecommerce.ecommerce.Security.services.UserDetailsServiceImpl;
import com.ecommerce.ecommerce.Services.*;
import com.ecommerce.ecommerce.payload.request.PaymentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SalesController.class)
class SalesControllerTest {
    private PaymentRequest paymentRequest;
    private static String createSaleUri = "/api/sales/create_sale";
    private String chargeId;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private SalesRepository salesRepository;
    @MockBean
    private ProductoService productoService;
    @MockBean
    private ProductSoldRepository productSoldRepository;
    @MockBean
    ProductSoldService productSoldService;
    @MockBean
    private ProductoRepository productoRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private ShoppingCarRepository shoppingCarRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    OrderService orderService;

    @MockBean
    SaleService saleService;
    @Autowired
    private MockMvc mockMvc;

    private MockMultipartFile employeeJson;
    private MockMultipartFile amount;
    private MockMultipartFile user_id;
    private MockMultipartFile order;
    private MockMultipartFile request;

    private ProductSold[] productSolds = new ProductSold[1];
    @BeforeEach
    void setUp() {
        chargeId = "12345678";
        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(100);
        paymentRequest.setDescription("Description");
        paymentRequest.setCurrency("USD");
        Token token = new Token();
        token.setId("1");
        paymentRequest.setToken(token);
        paymentRequest.setStripeEmail("Email@keke.com");

        ProductSold productSold = new ProductSold();
        productSold.setQuantity(1);
        productSold.setId(1L);
        productSolds[0] = productSold;

        employeeJson = new MockMultipartFile("productos", null,
                "application/json", asJsonString(productSolds).getBytes());
        amount = new MockMultipartFile("amount", null,
                "application/json", asJsonString(1.0).getBytes());
        user_id = new MockMultipartFile("user_id", null,
                "application/json", asJsonString(1L).getBytes());
        order = new MockMultipartFile("order", null,
                "application/json", asJsonString(new Order()).getBytes());
        request = new MockMultipartFile("request", null,
                "application/json", asJsonString(paymentRequest).getBytes());

    }

    @Test
    void shouldCreateSale() throws Exception {
/*
        User user = new User();
        user.setPassword("12345");
        user.setToken("12345");
        user.setId(1L);
        Optional<User> userOpt = Optional.ofNullable(user);
        Producto producto = new Producto();
        producto.setStock(100);
        Optional<Producto> opt = Optional.ofNullable(producto);
        given(productoRepository.findById(1L)).willReturn(opt);
        given(userService.findByID(1L)).willReturn(userOpt);
        Sale sale = new Sale();
        sale.setAmount(1.0);
        sale.setId(10L);
        sale.setDateAndTime("");
        Set<ProductSold> productos = new HashSet<>(Arrays.asList(productSolds));
        sale.setProductsSolds(productos);
        given(saleService.createSale(productSolds,1,1L)).willReturn(sale);
        given(productSoldService.addAllProductSold(productSolds,user,sale)).willReturn(Arrays.asList(productSolds));
        CreateSaleFrom createSaleFrom = new CreateSaleFrom(new ArrayList<>(Arrays.asList(productSolds)), 1, 1L, new Order(), paymentRequest);
        this.mockMvc.perform(multipart(createSaleUri,createSaleFrom)
                        .file(employeeJson)
                        .file(amount)
                        .file(user_id)
                        .file(order)
                        .file(request)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("OK")));

 */
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



/*
        PaymentRequest requestObj = new PaymentRequest();
        Token token = new Token();
        token.setId("12345678");
        requestObj.setToken(token);
        requestObj.setCurrency("USD");
        requestObj.setAmount(10);

        Producto productSold = new Producto();
        ProductSold[] productToSold = new ProductSold[1];
        productToSold[0] = new ProductSold();
        productToSold[0].setQuantity(1);
        List<ProductSold> productos = new ArrayList<>();
        productos.add(productToSold[0]);
        MockMultipartFile employeeJson = new MockMultipartFile("productos", null,
                "application/json", asJsonString(productos).getBytes());
        MockMultipartFile amount = new MockMultipartFile("amount", null,
                "application/json", asJsonString(1.0).getBytes());
        MockMultipartFile user_id = new MockMultipartFile("user_id", null,
                "application/json", asJsonString(1L).getBytes());
        MockMultipartFile order = new MockMultipartFile("order", null,
                "application/json", asJsonString(new Order()).getBytes());
        MockMultipartFile request = new MockMultipartFile("request", null,
                "application/json", asJsonString(requestObj).getBytes());

        User user = new User();
        Optional<Producto> opt = Optional.ofNullable(productSold);
        opt.get().setStock(100);
        Optional<User> usr = Optional.ofNullable(user);
        List<ProductSold> productSolds3 = new ArrayList<>();
        ProductSold productSoldAux = new ProductSold();
        productSoldAux.setQuantity(1);
        productSolds3.add(productSoldAux);
        CreateSaleFrom createSaleFrom = new CreateSaleFrom(productSolds3, 1, 1L, new Order(), requestObj);

        given(paymentService.charge(requestObj.getAmount(),requestObj.getCurrency(),requestObj.getToken().getId())).willReturn(chargeId);
        given(salesRepository.save(new Sale())).willReturn(new Sale());
        given(productoService.update(1L, new Producto())).willReturn(new Producto());
        given(productSoldRepository.save(new ProductSold())).willReturn(new ProductSold());
        given(orderRepository.save(new Order())).willReturn(new Order());
        given(productoRepository.findById(1L)).willReturn(opt);
        given(userRepository.findById(1L)).willReturn(usr);
        given(userService.findByID(1L)).willReturn(usr);
        given(saleService.createSale(productToSold,1,1L)).willReturn(new Sale());
        List<ProductSold> productSolds2 = new ArrayList<>();
        productSolds2.add(productSoldAux);
        given(productSoldService.addAllProductSold(productToSold,new User(),new Sale())).willReturn(productSolds2);
        //given(orderService.generatePdf(new Order(),new Sale(),new ArrayList<>(),new User())).willReturn(null);


        this.mockMvc.perform(multipart(createSaleUri, createSaleFrom)

                        .file(employeeJson)
                        .file(amount)
                        .file(user_id)
                        .file(order)
                        .file(request)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("OK")));

        //.andExpect(MockMvcResultMatchers.model().attributeErrorCount("addUserForm", 1)

         */

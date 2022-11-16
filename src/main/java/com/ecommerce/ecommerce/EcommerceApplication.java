package com.ecommerce.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@EnableSwagger2
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    //Define all details for app info
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Resuelveaqui Manage API")
                .description("Resuelveaqui Manage API reference for developers, use this api for explore all end points \n"
                        + " and fields included in requests for comunications with resuelveaqui services\n"
                )
                .termsOfServiceUrl("https://resuelveaqui.com/terms")
                .contact(new Contact("Alberto Martin", "", "support@resuelveaqui.com"))
                .license("Resuelveaqui License")
                .licenseUrl("https://resuelveaqui.com/es/ada-enterprise-core#contactus")
                .version("1.0")
                .build();
    }

    //main Swagger config definition
    @Bean
    public Docket resuelveaquiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("digitalthinking-spis")
                .apiInfo(apiInfo())
                //set up JWT input
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ecommerce"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag("Product Controller End Point", "All product requests and fields") {
                });
    }

    //define API key to include the header
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
    //configure JWT security with global Autorization Scope

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}

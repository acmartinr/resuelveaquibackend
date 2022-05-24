package com.ecommerce.ecommerce;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class imgConf implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry){
       WebMvcConfigurer.super.addResourceHandlers(registry);
       registry.addResourceHandler("/imagenEcc/**").addResourceLocations("file:/C:/imagenEcc/");

    }
}

package com.ecommerce.ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConf implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry){
       WebMvcConfigurer.super.addResourceHandlers(registry);
       exposeDirectory("product-images", registry);
       exposeDirectory("invoice", registry);
      // registry.addResourceHandler("/imagenEcc/**").addResourceLocations("file:/C:/imagenEcc/");

    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
        //=> for linux
        //    registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:///"+ uploadPath + "/");
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}

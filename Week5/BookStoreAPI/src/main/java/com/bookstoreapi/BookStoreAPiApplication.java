package com.bookstoreapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class BookStoreAPiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreAPiApplication.class, args);
        System.out.println("It's working Fine");
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
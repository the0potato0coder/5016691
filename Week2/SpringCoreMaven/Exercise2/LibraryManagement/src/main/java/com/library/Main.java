package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        // Load Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // Retrieve the BookService bean
        BookService bookService = context.getBean("bookService", BookService.class);
        // Display service to verify dependency injection
        bookService.displayService();
    }
}

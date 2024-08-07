package com.library.repository;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    public void displayRepository() {
        System.out.println("BookRepository is operational.");
    }
}

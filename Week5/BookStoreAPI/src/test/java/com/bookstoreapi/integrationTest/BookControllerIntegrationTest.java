package com.bookstoreapi.integrationTest;

import com.bookstoreapi.dto.BookDTO;
import com.bookstoreapi.model.Book;
import com.bookstoreapi.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();

        BookDTO book1DTO = new BookDTO(null, "3 Mistakes Of my Life", "Chetan Bhagat", 245.00);
        BookDTO book2DTO = new BookDTO(null, "Angles and Demons", "Dan Brown", 340.00);
        Book book1 = modelMapper.map(book1DTO, Book.class);
        Book book2 = modelMapper.map(book2DTO, Book.class);
        
        bookRepository.saveAll(Arrays.asList(book1, book2));
    }

    @Test
    void shouldGetAllBooks() throws Exception {
        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void shouldCreateBook() throws Exception {
        BookDTO newBook = new BookDTO(null, "Angles and Demons", "Dan Brown", 340.00);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Angles and Demons"));
    }
}
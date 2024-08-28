package com.bookstoreapi.controller;

import com.bookstoreapi.dto.BookDTO;
import com.bookstoreapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.*;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Management", description = "API for managing books in the online bookstore")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all books", description = "Retrieve a list of all books available in the bookstore")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooks() {
        List<EntityModel<BookDTO>> books = bookService.getAllBooks().stream()
                .map(this::addHATEOASLinks)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Total-Books", String.valueOf(books.size()));

        CollectionModel<EntityModel<BookDTO>> collectionModel = CollectionModel.of(books);
        collectionModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withSelfRel());

        return new ResponseEntity<>(collectionModel, headers, HttpStatus.OK);
    }

    @Operation(summary = "Get CSRF token", description = "Retrieve the CSRF token for security")
    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @Operation(summary = "Get book by ID", description = "Retrieve a book by its unique ID")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<BookDTO>> getBookById(
            @Parameter(description = "ID of the book to be retrieved") @PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        if (book != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Book-Found", "true");
            return new ResponseEntity<>(addHATEOASLinks(book), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Filter books", description = "Filter books by title and author")
    @GetMapping("/filter")
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> filterBooks(
            @Parameter(description = "Title of the book") @RequestParam(required = false) String title,
            @Parameter(description = "Author of the book") @RequestParam(required = false) String author) {
        List<EntityModel<BookDTO>> books = bookService.filterBooks(title, author).stream()
                .map(this::addHATEOASLinks)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Filter-Results", "Filtered by title and Author");

        CollectionModel<EntityModel<BookDTO>> collectionModel = CollectionModel.of(books);
        collectionModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).filterBooks(title, author)).withSelfRel());

        return new ResponseEntity<>(collectionModel, headers, HttpStatus.OK);
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the bookstore")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<BookDTO>> createBook(
            @Parameter(description = "Details of the new book to be created") @Valid @RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Header", "Book Created Successfully");
        headers.add("Location", "/books/" + createdBook.getId());

        return new ResponseEntity<>(addHATEOASLinks(createdBook), headers, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing book", description = "Update the details of an existing book by its ID")
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<BookDTO>> updateBook(
            @Parameter(description = "ID of the book to be updated") @PathVariable Long id,
            @Parameter(description = "Updated details of the book") @Valid @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        if (updatedBook != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Book-Updated", "true");
            return new ResponseEntity<>(addHATEOASLinks(updatedBook), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a book", description = "Delete a book from the bookstore by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to be deleted") @PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Book-Deleted", "true");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Helper method to add HATEOAS links
    private EntityModel<BookDTO> addHATEOASLinks(BookDTO bookDTO) {
        EntityModel<BookDTO> resource = EntityModel.of(bookDTO);

        // Add self link
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId())
        ).withSelfRel());

        // Add link to all books
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()
        ).withRel("all-books"));

        return resource;
    }
}
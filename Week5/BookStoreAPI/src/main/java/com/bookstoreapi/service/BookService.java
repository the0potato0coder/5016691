package com.bookstoreapi.service;

import com.bookstoreapi.dto.BookDTO;
import com.bookstoreapi.model.Book;
import com.bookstoreapi.exception.ResourceNotFoundException;
import com.bookstoreapi.repository.BookRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Custom metrics counters
    private final Counter booksCreatedCounter;
    private final Counter booksUpdatedCounter;

    // Constructor for initializing counters
    @Autowired
    public BookService(MeterRegistry meterRegistry) {
        this.booksCreatedCounter = meterRegistry.counter("custom.metrics.books.created");
        this.booksUpdatedCounter = meterRegistry.counter("custom.metrics.books.updated");
    }

    // Listing all Books
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    // Listing the Desired Book by Id
    public BookDTO getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(this::convertEntityToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = convertDTOToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);

        // Increment the custom metric for book creation
        booksCreatedCounter.increment();

        return convertEntityToDTO(savedBook);
    }

    @Transactional
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Not Found"));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());

        try {
            Book updatedBook = bookRepository.save(book);

            // Increment the custom metric for book updates
            booksUpdatedCounter.increment();

            return convertEntityToDTO(updatedBook);
        } catch (OptimisticLockException e) {
            throw new RuntimeException("Failed to update book. It may have been updated by another transaction.");
        }
    }

    // To Delete a Book
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Not Found"));
        bookRepository.delete(book);
    }

    // To find Book by author name and title name
    public List<BookDTO> filterBooks(String title, String author) {
        List<Book> books;
        if (title != null && author != null) {
            books = bookRepository.findByTitleContainingAndAuthorContaining(title, author);
        } else if (title != null) {
            books = bookRepository.findByTitleContaining(title);
        } else if (author != null) {
            books = bookRepository.findByAuthorContaining(author);
        } else {
            books = bookRepository.findAll();
        }
        return books.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private BookDTO convertEntityToDTO(Book book) {
        return this.modelMapper.map(book, BookDTO.class);
    }

    private Book convertDTOToEntity(BookDTO bookDTO) {
        return this.modelMapper.map(bookDTO, Book.class);
    }
}
package com.parker.customerwebsite.services.impl;

import com.parker.customerwebsite.exceptions.DuplicateBookException;
import com.parker.customerwebsite.exceptions.NoSuchBookException;
import com.parker.customerwebsite.model.Book;
import com.parker.customerwebsite.repositories.BookRepository;
import com.parker.customerwebsite.repositories.CustomerRepository;
import com.parker.customerwebsite.services.BookService;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    public BookServiceImpl(BookRepository bookRepository, CustomerRepository customerRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Book saveBook(Book book) throws DuplicateBookException {
        if (bookRepository.findDistinctByIsbn(book.getIsbn()) != null) {
            throw new DuplicateBookException("This book already exists in our system.");
        } else {
            return bookRepository.save(book);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> saveAllBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    @Override
    public List<Book> getAvailableBooks() throws NoSuchBookException {
        List<Book> availableBooks = bookRepository.findAll();
        availableBooks.removeIf(book -> book.getCustomer() != null);
        if (availableBooks.isEmpty()) {
            throw new NoSuchBookException("There aren't any books available at this time.");
        } else {
            return availableBooks;
        }
    }

    @Override
    public void deleteBook(Long id) throws NoSuchBookException {
        if (bookRepository.findById(id).isEmpty()) {
            throw new NoSuchBookException("A book with that id does not exist");
        } else {
            bookRepository.deleteById(id);
        }
    }

    @Override
    public Book getBookByBookId(Long id) throws NoSuchBookException {
        if (bookRepository.findById(id).isEmpty()) {
            throw new NoSuchBookException("A book with that id does not exist.");
        } else {
            return bookRepository.findById(id).get();
        }
    }

    @Override
    public Book checkoutBook(Long bookId, Long customerId) {
        Book book = bookRepository.getById(bookId);
        book.setCustomer(customerRepository.findById(customerId).get());
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getCustomerBooks(Long id) throws NoSuchBookException {
        if (bookRepository.findAllByCustomerId(id).isEmpty()) {
            throw new NoSuchBookException("Customer does not have any books checked out.");
        } else {
            return bookRepository.findAllByCustomerId(id);
        }
    }

    @Override
    public Book returnBook(Long bookId) {
        Book book = bookRepository.getById(bookId);
        book.setCustomer(null);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }
}

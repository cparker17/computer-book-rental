package com.parker.customerwebsite.services;

import com.parker.customerwebsite.exceptions.DuplicateBookException;
import com.parker.customerwebsite.exceptions.NoSuchBookException;
import com.parker.customerwebsite.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    Book saveBook(Book book) throws DuplicateBookException;
    List<Book> getAllBooks();
    List<Book> saveAllBooks(List<Book> books);
    List<Book> getAvailableBooks() throws NoSuchBookException;
    void deleteBook(Long id) throws NoSuchBookException;
    Book getBookByBookId(Long id) throws NoSuchBookException;
    Book checkoutBook(Long bookId, Long customerId);
    List<Book> getCustomerBooks(Long id) throws NoSuchBookException;
    Book returnBook(Long bookId);
    Book updateBook(Book book);
}

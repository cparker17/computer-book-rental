package com.parker.computerbookrental.services;

import com.parker.computerbookrental.exceptions.DuplicateBookException;
import com.parker.computerbookrental.exceptions.NoSuchBookException;
import com.parker.computerbookrental.model.Book;
import com.parker.computerbookrental.model.BookHistory;
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
    Book checkoutBook(Long bookId, Long userId);
    List<Book> getUserBooks(Long id) throws NoSuchBookException;
    Book returnBook(Long id);
    Book updateBook(Book book);
    List<Book> getNewArrivals() throws NoSuchBookException;
    List<Book> getSearchResults(String searchText);
    List<BookHistory> getBookRentalHistory(Long id);
}

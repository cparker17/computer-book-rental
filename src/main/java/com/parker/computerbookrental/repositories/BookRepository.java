package com.parker.computerbookrental.repositories;

import com.parker.computerbookrental.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByUserId(Long id);
    Book findDistinctByIsbn(String isbn);
    List<Book> findBooksByDateAddedIsAfter(LocalDate date);
    List<Book> findBooksByTitleContaining(String searchText);
    List<Book> findBooksByAuthorContaining(String searchText);
    List<Book> findBooksByIsbnContaining(String searchText);
    Book findTopByOrderByDateAddedDesc();
}

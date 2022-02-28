package com.parker.computerbookrental.services.impl;

import com.parker.computerbookrental.exceptions.DuplicateUserException;
import com.parker.computerbookrental.exceptions.NoSuchUserException;
import com.parker.computerbookrental.model.Book;
import com.parker.computerbookrental.model.RentalHistory;
import com.parker.computerbookrental.model.security.Role;
import com.parker.computerbookrental.model.User;
import com.parker.computerbookrental.repositories.BookRepository;
import com.parker.computerbookrental.repositories.RentalHistoryRepository;
import com.parker.computerbookrental.repositories.RoleRepository;
import com.parker.computerbookrental.repositories.UserRepository;
import com.parker.computerbookrental.services.SecurityUserService;
import com.parker.computerbookrental.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    final UserRepository userRepository;

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RentalHistoryRepository rentalHistoryRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User registerAccount(User user) throws DuplicateUserException {
        if (userRepository.findDistinctByEmail(user.getEmail()) != null) {
            throw new DuplicateUserException("This customer already exists in the system.");
        } else {
            Role role = roleRepository.findRoleById(1L);
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    @Override
    public User getUser(Long id) throws NoSuchUserException {
        Optional optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NoSuchUserException("A customer with that id does not exist.");
        } else {
            return (User) optionalUser.get();
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) throws NoSuchUserException {
        if (userRepository.findById(id).isEmpty()) {
            throw new NoSuchUserException("A customer with that id does not exist.");
        } else {
            userRepository.deleteById(id);
        }
    }

    @Transactional
    public List<User> saveAllUsers(List<User> userList) {
        return userRepository.saveAll(userList);
    }

    @Override
    @Transactional
    public void returnBook(Long id, User user) {
        List<Book> books = new ArrayList<>();
        for (Book book : user.getBooks()) {
            if (book.getId() != id) {
                books.add(book);
            } else {
                updateRentalHistory(user, book);
            }
        }
        user.setBooks(books);
        userRepository.save(user);
    }

    private void updateRentalHistory(User user, Book book) {
        RentalHistory rentalHistory = RentalHistory.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .dateRented(book.getDateRented())
                .dateReturned(book.getDateReturned())
                .build();
        user.addRentalHistory(rentalHistory);
        rentalHistoryRepository.save(rentalHistory);
    }

    @Override
    public void checkoutBook(Book book, User user) {
        user.addBook(book);
        userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public List<RentalHistory> getUserRentalHistory(Long id) {
        User user = userRepository.findById(id).get();
        return user.getRentalHistoryList();
    }
}

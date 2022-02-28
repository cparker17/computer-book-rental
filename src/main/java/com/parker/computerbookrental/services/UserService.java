package com.parker.computerbookrental.services;

import com.parker.computerbookrental.exceptions.DuplicateUserException;
import com.parker.computerbookrental.exceptions.NoSuchUserException;
import com.parker.computerbookrental.model.Book;
import com.parker.computerbookrental.model.RentalHistory;
import com.parker.computerbookrental.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    User registerAccount(User user) throws DuplicateUserException;
    User getUser(Long id) throws NoSuchUserException;
    void deleteUser(Long id) throws NoSuchUserException;
    List<User> saveAllUsers(List<User> userList);
    void returnBook(Long id, User user);
    void checkoutBook(Book book, User user);
    User updateUser(User user);
    List<RentalHistory> getUserRentalHistory(Long id);
}

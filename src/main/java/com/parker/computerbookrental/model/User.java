package com.parker.computerbookrental.model;

import com.parker.computerbookrental.model.Address;
import com.parker.computerbookrental.model.Book;
import com.parker.computerbookrental.model.security.Role;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull(message = "First name required.")
    private String firstName;

    @NotNull(message = "Last name required.")
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id")
    private Address address;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Book> books;

    @OneToMany
    private List<RentalHistory> rentalHistoryList;

    public void addBook(Book book) {
        if (books.isEmpty()) {
            books = new ArrayList<>();
        }
        books.add(book);
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private Role role;

    @Column(nullable = false)
    private boolean isAccountNonExpired = true;

    @Column(nullable = false)
    private boolean isAccountNonLocked = true;

    @Column(nullable = false)
    private boolean isCredentialsNonExpired = true;

    @Column(nullable = false)
    private boolean isEnabled = true;

    public void addRentalHistory(RentalHistory rentalHistory) {
        if (rentalHistoryList == null) {
            rentalHistoryList = new ArrayList<>();
        } else {
            rentalHistoryList.add(rentalHistory);
        }
    }
}

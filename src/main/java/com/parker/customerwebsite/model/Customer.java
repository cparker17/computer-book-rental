package com.parker.customerwebsite.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Builder
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_details_id")
    private CustomerDetails customerDetails;

    @NotNull(message = "First name required.")
    private String firstName;

    @NotNull(message = "Last name required.")
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(unique = true)
    private String emailAddress;

    @OneToMany(mappedBy = "customer")
    private List<Book> books;

    public void addBook(Book book) {
        if (books.isEmpty()) {
            books = new ArrayList<>();
        }
        books.add(book);
    }
}

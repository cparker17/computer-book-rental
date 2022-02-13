package com.parker.customerwebsite.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Customer name required.")
    private String fullName;

    private String emailAddress;

    private Integer age;

    @NotNull(message = "Customer address required.")
    private String address;

    @OneToMany
    private List<Book> books;
}

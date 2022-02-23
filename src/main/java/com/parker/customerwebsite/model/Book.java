package com.parker.customerwebsite.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
@Builder
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String isbn;

    @ManyToOne
    private Customer customer;
}

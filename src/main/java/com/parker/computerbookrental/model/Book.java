package com.parker.computerbookrental.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @NotBlank(message = "Title required.")
    private String title;

    @NotNull
    @NotBlank(message = "Author required.")
    private String author;

    @NotNull
    @NotBlank(message = "ISBN required.")
    private String isbn;

    private String imageFile;

    private LocalDate dateAdded;

    private LocalDate dateRented;

    private LocalDate dateReturned;

    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "book_id")
    private List<BookHistory> bookHistoryList;

    public void addRentalHistory(BookHistory rentalHistory) {
        if (bookHistoryList == null) {
            bookHistoryList = new ArrayList<>();
        } else {
            bookHistoryList.add(rentalHistory);
        }
    }
}

package com.parker.computerbookrental.repositories;

import com.parker.computerbookrental.model.BookHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookHistoryRepository extends JpaRepository<BookHistory, Long> {
}

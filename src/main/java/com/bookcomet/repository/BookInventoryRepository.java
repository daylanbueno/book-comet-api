package com.bookcomet.repository;

import com.bookcomet.entity.Book;
import com.bookcomet.entity.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
    BookInventory findByBook(Book book);
}

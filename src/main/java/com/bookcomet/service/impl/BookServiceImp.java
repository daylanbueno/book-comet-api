package com.bookcomet.service.impl;

import com.bookcomet.converters.BookConverter;
import com.bookcomet.dto.DtoBook;
import com.bookcomet.entity.Book;
import com.bookcomet.entity.BookInventory;
import com.bookcomet.exceptions.BusinessException;
import com.bookcomet.repository.BookInventoryRepository;
import com.bookcomet.repository.BookRepository;
import com.bookcomet.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;

    private final BookConverter bookConverter;

    private final BookInventoryRepository bookInventoryRepository;

    @Override
    public DtoBook save(DtoBook dtoBook) {

        if (bookRepository.existsByNameAndAuthor(dtoBook.getName(), dtoBook.getAuthor()))
            throw new BusinessException("The book is already registered");


        Book entity = bookConverter.converterToEntity(dtoBook);

        return bookConverter.converterToDto(bookRepository.save(entity));
    }

    @Override
    public DtoBook update(DtoBook dtoBook) {

        if (dtoBook.getId() == null)
            throw new BusinessException("Book ID is required for change.");

        Book book = bookRepository.save(bookConverter.converterToEntity(dtoBook));

        return bookConverter.converterToDto(book);
    }

    @Override
    public void deleteById(Long id) {

        Book book = bookRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException("There is no book with the given ID"));

       BookInventory inventory = bookInventoryRepository.findByBook(book);

       if (inventory != null && inventory.getQuantity() > 0)
           throw  new BusinessException("Cannot delete book because it has positive inventory.");

       bookRepository.deleteById(id);
    }

    @Override
    public List<DtoBook> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookConverter::converterToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoBook> findByAuthor(String author) {

       List<Book> books = bookRepository.findByAuthor(author);

        return books.stream()
                .map(bookConverter::converterToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoBook> findByPublisher(String publisher) {

        List<Book> books = bookRepository.findByPublisher(publisher);

        return books.stream()
                .map(bookConverter::converterToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long setInventory(Long bookId, Long quantity) {

        Book book = bookRepository
                .findById(bookId).orElseThrow(() -> new IllegalArgumentException("There is no book with the given ID"));

        if (quantity < 0)
            throw new BusinessException("Quantity cannot be negative");

        BookInventory inventoryExist = bookInventoryRepository.findByBook(book);

        if (inventoryExist != null) {

            inventoryExist.setQuantity(quantity);

            return bookInventoryRepository
                    .save(inventoryExist)
                    .getId();
        }

        BookInventory bookInventory = createInventory(quantity, book);
        bookInventoryRepository.save(bookInventory);
        return bookInventory.getId();
    }

    @Override
    public void deleteInventoryById(Long id) {
        if (!bookInventoryRepository.existsById(id))
            throw new  IllegalArgumentException("There is no Inventory with the given ID");

        bookInventoryRepository.deleteById(id);
    }

    private static BookInventory createInventory(Long quantity, Book book) {
        return BookInventory.builder()
                .book(book)
                .quantity(quantity).build();
    }
}

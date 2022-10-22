package com.bookcomet.service.impl;

import com.bookcomet.converters.BookConverter;
import com.bookcomet.dto.DtoBook;
import com.bookcomet.entity.Book;
import com.bookcomet.exceptions.BusinessException;
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
    @Override
    public DtoBook save(DtoBook dtoBook) {
        Book entity = bookConverter.converterToEntity(dtoBook);
        Book newBook = bookRepository.save(entity);
        return bookConverter.converterToDto(newBook);
    }

    @Override
    public DtoBook update(DtoBook dtoBook) {

        if (dtoBook.getId() == null) {
            throw new BusinessException("Book ID is required for change.");
        }

        Book book = bookRepository.save(bookConverter.converterToEntity(dtoBook));

        return bookConverter.converterToDto(book);
    }

    @Override
    public void deleteById(Long id) {

        bookRepository
                .findById(id).orElseThrow(()  -> new BusinessException("There is no book with the given ID"));

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
}

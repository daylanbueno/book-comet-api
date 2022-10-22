package com.bookcomet.converters;

import com.bookcomet.dto.DtoBook;
import com.bookcomet.entity.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookConverter {
    private final ModelMapper modelMapper;

    public DtoBook converterToDto(Book book) {
        return modelMapper.map(book, DtoBook.class);
    }

    public Book converterToEntity(DtoBook dtoBook) {
        return modelMapper.map(dtoBook, Book.class);
    }
}

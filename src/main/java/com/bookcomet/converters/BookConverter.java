package com.bookcomet.converters;

import com.bookcomet.BookType;
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
        DtoBook dto = modelMapper.map(book, DtoBook.class);
        if (BookType.BOOK.name().equals(book.getType().name())){
            dto.setType(BookType.BOOK.name());
        } else {
            dto.setType(BookType.EBOOK.name());
        }
        return dto;
    }

    public Book converterToEntity(DtoBook dtoBook) {
        Book entity = modelMapper.map(dtoBook, Book.class);

       if(BookType.EBOOK.name().equals(dtoBook.getType())) {
           entity.setType(BookType.EBOOK);
       } else {
           entity.setType(BookType.BOOK);
       }

        return entity;

    }
}

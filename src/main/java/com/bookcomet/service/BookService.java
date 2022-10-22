package com.bookcomet.service;

import com.bookcomet.dto.DtoBook;

import java.util.List;

public interface BookService {
    public DtoBook save(DtoBook dtoBook);

    public DtoBook update(DtoBook dtoBook);

    public void deleteById(Long id);

    public List<DtoBook> findAll();

    public List<DtoBook> findByAuthor(String author);

    public List<DtoBook> findByPublisher(String publisher);

}

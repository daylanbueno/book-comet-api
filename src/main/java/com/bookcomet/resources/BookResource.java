package com.bookcomet.resources;

import com.bookcomet.dto.DtoBook;
import com.bookcomet.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookResource {

    private final BookService bookService;

    @GetMapping
    public List<DtoBook> findAll() {
        return bookService.findAll();
    }

    @PostMapping
    public DtoBook save(@RequestBody DtoBook dtoBook) {
        return bookService.save(dtoBook);
    }

    @PutMapping
    public DtoBook update(@RequestBody DtoBook dtoBook) {
        return bookService.update(dtoBook);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @GetMapping("/author/{author}")
    public List<DtoBook> findByAuthor(@PathVariable String author) {
        return bookService.findByAuthor(author);
    }

    @GetMapping("/publisher/{publisher}")
    public List<DtoBook> findByPublisher(@PathVariable String publisher) {
        return bookService.findByPublisher(publisher);
    }

    @PostMapping("/{id}/inventories/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addInvetory(@PathVariable("id")  Long idBook, @PathVariable Long quantity) {
        return bookService.setInventory(idBook, quantity);
    }

    @DeleteMapping("/inventories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable("id")  Long id) {
        bookService.deleteInventoryById(id);
    }
}

package com.bookcomet.resources;

import com.bookcomet.dto.DtoBook;
import com.bookcomet.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookResource.class)
public class BookResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final static Long idBook = 123L;

    @Test
    @DisplayName("should get all the books  successfully")
    public void shou() throws Exception {
        DtoBook dtoBook = DtoBook.builder()
                .id(idBook)
                .name("Clean Architecture")
                .author("Robert")
                .publisher("Pearson")
                .yearOfPublication(2008).build();

        Mockito.when(bookService.findAll()).thenReturn(Collections.singletonList(dtoBook));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].id").value(dtoBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].name").value(dtoBook.getName()))
                .andReturn();
    }

    @Test
    @DisplayName("should save a book successfully")
    public void shouldSaveBook() throws Exception {
        DtoBook dtoBook = DtoBook.builder().name("Clean Architecture").author("Robert").publisher("Pearson").yearOfPublication(2008).build();
        DtoBook newBook = DtoBook.builder().id(idBook).name("Clean Architecture").author("Robert").publisher("Pearson").yearOfPublication(2008).build();

        Mockito.when(bookService.save(dtoBook)).thenReturn(newBook);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dtoBook));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(newBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(newBook.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(newBook.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("publisher").value(newBook.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("yearOfPublication").value(newBook.getYearOfPublication()))
                .andReturn();
    }

    @Test
    @DisplayName("should save a book successfully")
    public void updateBook() throws Exception {
        DtoBook dtoBook = DtoBook.builder().id(idBook).name("Clean Architecture").author("Robert").publisher("Pearson").yearOfPublication(2008).build();
        DtoBook updatedBook = DtoBook.builder().id(idBook).name("Clean teste").author("Robert Martins").publisher("Pearson").yearOfPublication(2008).build();

        Mockito.when(bookService.update(dtoBook)).thenReturn(updatedBook);

        RequestBuilder request = MockMvcRequestBuilders.put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dtoBook));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(updatedBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(updatedBook.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(updatedBook.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("publisher").value(updatedBook.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("yearOfPublication").value(updatedBook.getYearOfPublication()))
                .andReturn();
    }

    @Test
    @DisplayName("should delete  a book by id  successfully")
    public void shouldDeleteByIdSuccessfully() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/books/"+idBook)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("should get all the books by author successfully")
    public void shouldGetAllBooksByAuthor() throws Exception {
        String author = "Robert";
        DtoBook dtoBook = DtoBook.builder()
                .id(idBook)
                .name("Clean Architecture")
                .author("Robert")
                .publisher("Pearson")
                .yearOfPublication(2008).build();

        Mockito.when(bookService.findByAuthor(Mockito.anyString())).thenReturn(Collections.singletonList(dtoBook));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/books/author/"+author)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].id").value(dtoBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].name").value(dtoBook.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].author").value(dtoBook.getAuthor()))
                .andReturn();
    }

    @Test
    @DisplayName("should get all the books by publisher successfully")
    public void shouldGetAllBooksByPublisher() throws Exception {
        String publisher = "Robert";
        DtoBook dtoBook = DtoBook.builder()
                .id(idBook)
                .name("Clean Architecture")
                .author("Robert")
                .publisher("Pearson")
                .yearOfPublication(2008).build();

        Mockito.when(bookService.findByPublisher(Mockito.anyString())).thenReturn(Collections.singletonList(dtoBook));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/books/publisher/"+publisher)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].id").value(dtoBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].name").value(dtoBook.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].publisher").value(dtoBook.getPublisher()))
                .andReturn();
    }

    @Test
    @DisplayName("should add the book to the inventory successfully")
    public void shouldAddTheBookToTheInventarySuccessfully() throws Exception {
        Long id = 111L;
        Mockito.when(bookService.setInventory(Mockito.anyLong(), Mockito.anyLong())).thenReturn(id);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/books/123/inventories/25")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("should delete  a inventary by id  successfully")
    public void shouldDeleteInventaryByIdSuccessfully() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/books/inventories/"+idBook)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();
    }
}

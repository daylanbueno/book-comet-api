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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookResource.class)
public class BookResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("should save a book successfully")
    public void showSaveBook() throws Exception {
        DtoBook dtoBook = DtoBook.builder().name("Clean Architecture").author("Robert").publisher("Pearson").yearOfPublication(2008).build();
        DtoBook newBook = DtoBook.builder().id(123l).name("Clean Architecture").author("Robert").publisher("Pearson").yearOfPublication(2008).build();

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

}

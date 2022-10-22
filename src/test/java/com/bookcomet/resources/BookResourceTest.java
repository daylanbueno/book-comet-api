package com.bookcomet.resources;

import com.bookcomet.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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
//        UsuarioDto usuarioDto = UsuarioDto.builder().email("admin@gmail.com").cpf("85186122093").senha("teste").build();
//        UsuarioDto usuarioCadastrado = UsuarioDto.builder().email("admin@gmail.com").cpf("85186122093").senha("teste").id(1l).build();
//
//        Mockito.when(usuarioService.salvarUsuario(usuarioDto)).thenReturn(usuarioCadastrado);
//
//        RequestBuilder request = MockMvcRequestBuilders.post("/api/usuarios")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(usuarioDto));
//
//        mockMvc.perform(request)
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("cpf").value(usuarioCadastrado.getCpf()))
//                .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuarioCadastrado.getEmail()))
//                .andReturn();
    }

}

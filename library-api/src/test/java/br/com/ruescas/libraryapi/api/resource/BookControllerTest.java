package br.com.ruescas.libraryapi.api.resource;

import br.com.ruescas.libraryapi.api.dto.BookDTO;
import br.com.ruescas.libraryapi.exception.BusinessException;
import br.com.ruescas.libraryapi.model.entity.Book;
import br.com.ruescas.libraryapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BOOK_API = "/api/books";
    @Autowired
    MockMvc mvc;

    @MockBean
    BookService service;

    @Test
    @DisplayName("Deve filtrar livros")
    public void findBooksTest() throws Exception {
        Long id = 1L;
        Book book = Book.builder()
                .id(id)
                .title(getBookDTO().getTitle())
                .author(getBookDTO().getAuthor())
                .isbn(getBookDTO().getIsbn())
                .build();

        BDDMockito.given(service.find(Mockito.any(Book.class), Mockito.any(Pageable.class)))
                .willReturn( new PageImpl<Book>(Arrays.asList(book), PageRequest.of(0, 100), 1));


        String queryString = String.format("?title=%s&author=%s&page=0&size=100",
                book.getTitle(),
                book.getAuthor());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BOOK_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", Matchers.hasSize(1)))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(100))
                .andExpect(jsonPath("pageable.pageNumber").value(0));
    }

    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    public void createBookTest() throws Exception {


        BookDTO bookDTO = getBookDTO();

        Book savedBook = Book.builder()
                .id(1L)
                .title("As aventuras")
                .author("Arthur")
                .isbn("001")
                .build();

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);

        String json = new ObjectMapper().writeValueAsString(bookDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                // verifica o status de resposta
                .andExpect(status().isCreated())
                // verifica o json de resposta
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("author").value(bookDTO.getAuthor()))
                .andExpect(jsonPath("isbn").value(bookDTO.getIsbn()));
    }


    @Test
    @DisplayName("Deve lançar erro de validacao quando nao houver dados suficientes para criacao do livro.")
    public void createInvalidBookTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(3)));

    }

    @Test
    @DisplayName("Deve lancar erro ao tentar cadastrar um livro com isbn já utilizado")
    public void createBookWithDuplicatedIsbn() throws Exception {

        String json = new ObjectMapper().writeValueAsString(getBookDTO());
        String mensagemErro = "Isbn já cadastrado";
        BDDMockito.given(service.save(Mockito.any(Book.class))).willThrow(new BusinessException("Isbn já cadastrado"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(mensagemErro));

    }

    @Test
    @DisplayName("Deve obter informacoes de um livro")
    public void detailsBookTest() throws Exception {
        // cenario (given)
        Long id = 1L;
        Book book = Book.builder().id(id).title("As aventuras").author("Joao").isbn("123").build();
        BDDMockito.given(service.findById(id)).willReturn(Optional.of(book));

        // execucao (when)
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BOOK_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        // verificacao (then)
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(book.getTitle()))
                .andExpect(jsonPath("author").value(book.getAuthor()))
                .andExpect(jsonPath("isbn").value(book.getIsbn()));
    }

    @Test
    @DisplayName("Deve retornar resources not found quando o livro procurado não existir")
    public void bookNotFoundTest() throws Exception {

        BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BOOK_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isNotFound());


    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deleteBookTest() throws Exception {

        BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(Book.builder().id(1L).build()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(BOOK_API.concat("/" + 1));
        mvc.perform(request).andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar o livro para deletar")
    public void bookDeleteNotFound() throws Exception {

        BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.empty());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(BOOK_API.concat("/" + 1));

        mvc.perform(request).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Deve atualizar um livro")
    public void updateBookTest() throws Exception {
        Long id = 1L;
        String json = new ObjectMapper().writeValueAsString(getBookDTO());

        Book updatingBook = Book.builder().id(1L).title("some title").author("some author").isbn("321").build();
        BDDMockito.given(service.findById(id)).willReturn(Optional.of(updatingBook));

        Book updatedBook = Book.builder().id(id).title("As aventuras").author("Arthur").isbn("001").build();
        BDDMockito.given(service.update(updatingBook)).willReturn(updatedBook);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BOOK_API.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(getBookDTO().getTitle()))
                .andExpect(jsonPath("author").value(getBookDTO().getAuthor()))
                .andExpect(jsonPath("isbn").value(getBookDTO().getIsbn()));
    }

    @Test
    @DisplayName("Deve Retornar Not Found ao atualizar livro")
    public void updateBookNotFound() throws Exception {
        String json = new ObjectMapper().writeValueAsString(getBookDTO());

        BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder response = MockMvcRequestBuilders
                .put(BOOK_API.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(response)
                .andExpect(status().isNotFound());

    }

    private static BookDTO getBookDTO() {
        return BookDTO.builder()
                .title("As aventuras")
                .author("Arthur")
                .isbn("001")
                .build();
    }

}

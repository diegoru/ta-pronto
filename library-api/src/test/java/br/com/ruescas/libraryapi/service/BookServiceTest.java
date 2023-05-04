package br.com.ruescas.libraryapi.service;

import br.com.ruescas.libraryapi.exception.BusinessException;
import br.com.ruescas.libraryapi.model.entity.Book;
import br.com.ruescas.libraryapi.model.repository.BookRepository;
import br.com.ruescas.libraryapi.service.impl.BookServiceImpl;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;
    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve filtrar livros pelas propriedades")
    public void findBookTest(){
        Book book = getBook();

        List<Book> list = Arrays.asList(book);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> page = new PageImpl<Book>(list, pageRequest, 1);
        Mockito.when(repository.findAll(Mockito.any(Example.class), any(PageRequest.class)))
                .thenReturn(page);

        Page<Book> result = service.find(book, pageRequest);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(list);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("Deve salvar o livro")
    public void bookSaveTest() {
        Book book =Book.builder().id(1L).title("As aventuras").author("Fulano").isbn("123").build();
        when(repository.existsByIsbn(anyString())).thenReturn(false);
        when(repository.save(book)).thenReturn(
                book);

        Book savedBook = service.save(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("As aventuras");
        assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
        assertThat(savedBook.getIsbn()).isEqualTo("123");

    }

    @Test
    @DisplayName("Deve lançar erro de negócio ao tentar  salvar um livro com isbn duplicado")
    public void shouldNotSaveABookDuplicatedIsbn() {
        when(repository.existsByIsbn(anyString())).thenReturn(true);
        Throwable exception = catchThrowable(() -> service.save(getBook()));
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn já cadastrado");
        verify(repository, never()).save(getBook());
    }

    @Test
    @DisplayName("Deve obter um livro por Id")
    public void getByIdTest() {
        Long id = 1L;
        Book book = getBook();
        book.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = service.findById(id);

        assertThat(foundBook.isPresent()).isTrue();
        assertThat(foundBook.get().getId()).isEqualTo(id);
        assertThat(foundBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(foundBook.get().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(foundBook.get().getIsbn()).isEqualTo(book.getIsbn());

    }

    @Test
    @DisplayName("Deve retornar vazio ao obter um livro por Id quando ele não existe na base")
    public void bookNotFoundByIdTest() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Book> foundBook = service.findById(id);

        assertThat(foundBook.isPresent()).isFalse();

    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void bookDeleteTest() {
        Book book = getBook();
        book.setId(1L);
        Assertions.assertDoesNotThrow(() -> service.delete(book));
        verify(repository, times(1)).delete(book);
    }

    @Test
    @DisplayName("Deve ocorrer erro ao tentar deletar um livro inexistente.")
    public void deleteInvalidBookTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(null));
        verify(repository, never()).delete(null);
    }

    @Test
    @DisplayName("Deve lancar IllegalArgument livro com ID nulo")
    public void bookDeleteIdNullIllegalArgumentTest() {
        Book book = getBook();
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(book));
    }


    @Test
    @DisplayName("Deve atulizar um livro")
    public void bookUpdateTest() {
        Long id = 1L;


        Book updatingBook = Book.builder().id(1L).title("titulo").author("author").isbn("999").build();
        Book updatedBook = Book.builder().id(id).title("As aventuras").author("Arthur").isbn("123").build();

        when(repository.save(updatingBook)).thenReturn(updatedBook);

        Book book = service.update(updatingBook);

        assertThat(book.getId()).isEqualTo(id);
        assertThat(book.getTitle()).isEqualTo(updatedBook.getTitle());
        assertThat(book.getAuthor()).isEqualTo(updatedBook.getAuthor());
        assertThat(book.getIsbn()).isEqualTo(updatedBook.getIsbn());

    }

    @Test
    @DisplayName("Deve lancar erro livro nulo")
    public void bookUpdateNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(null));
        verify(repository, never()).save(null);
    }

    @Test
    @DisplayName("Deve lancar erro livro id nulo")
    public void bookUpdateIDNullTest() {
        Book book = getBook();
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(book));
        verify(repository, never()).save(null);
    }

    private static Book getBook() {
        return Book.builder()
                .title("As aventuras")
                .author("Fulano")
                .isbn("123")
                .build();
    }
}

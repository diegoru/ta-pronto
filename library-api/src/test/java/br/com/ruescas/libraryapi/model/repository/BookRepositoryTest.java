package br.com.ruescas.libraryapi.model.repository;

import br.com.ruescas.libraryapi.model.entity.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir o livro na base com o isbn informado")
    public void returnTrueWhenIsbnExists() {
        // cenario
        String isbn = "123";
        Book book = getBook(isbn);

        entityManager.persist(book);

        // execucao
        boolean exists = repository.existsByIsbn(isbn);

        //verificacao
        assertThat(exists).isTrue();
    }



    @Test
    @DisplayName("Deve retornar falso quando nao existir o livro na base com o isbn informado")
    public void returnFalseWhenIsbnDoesntExists() {
        // cenario
        String isbn = "123";

        // execucao
        boolean exists = repository.existsByIsbn(isbn);

        //verificacao
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve obter um livro por id")
    public void findByIdTest() {
        Book book = getBook("123");

        entityManager.persist(book);
        Optional<Book> foundBook = repository.findById(book.getId());

        assertThat(foundBook.isPresent()).isTrue();

    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest() {
        Book book = getBook("321");
        entityManager.persist(book);
        Book savedBook = repository.save(book);

        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve remover um livro")
    public void deleteBookTest() {
        Book book = getBook("321");
        entityManager.persist(book);

        Book foundBook = entityManager.find(Book.class, book.getId());
        repository.delete(foundBook);

        Book deletedBook = entityManager.find(Book.class, book.getId());
        assertThat(deletedBook).isNull();

    }

    private static Book getBook(String isbn) {
        return Book.builder().title("As aventuras").author("João").isbn(isbn).build();
    }
}

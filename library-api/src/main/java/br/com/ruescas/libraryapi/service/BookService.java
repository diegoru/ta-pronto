package br.com.ruescas.libraryapi.service;

import br.com.ruescas.libraryapi.api.dto.BookDTO;
import br.com.ruescas.libraryapi.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {
    Book save(Book book);

    Optional<Book> findById(Long id);

    void delete(Book book);

    Book update(Book book);

    Page<Book> find(Book filter, Pageable pageRequest);
}

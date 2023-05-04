package br.com.ruescas.libraryapi.service.impl;

import br.com.ruescas.libraryapi.exception.BusinessException;
import br.com.ruescas.libraryapi.model.entity.Book;
import br.com.ruescas.libraryapi.model.repository.BookRepository;
import br.com.ruescas.libraryapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public Book save(Book book) {
        if (repository.existsByIsbn(book.getIsbn())) throw new BusinessException("Isbn já cadastrado");
        return repository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Book book) {
        if (book == null || book.getId() == null) throw new IllegalArgumentException("Book id cant ne null.");
        this.repository.delete(book);
    }

    @Override
    public Book update(Book book) {
        if (book == null || book.getId() == null) throw new IllegalArgumentException("Book id cant ne null.");
        return this.repository.save(book);
    }

    @Override
    public Page<Book> find(Book filter, Pageable pageRequest) {
        Example<Book> example = Example.of(filter, ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING)
        );

        return repository.findAll(example, pageRequest);
    }
}

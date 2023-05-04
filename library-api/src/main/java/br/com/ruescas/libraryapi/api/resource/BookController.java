package br.com.ruescas.libraryapi.api.resource;

import br.com.ruescas.libraryapi.api.dto.BookDTO;
import br.com.ruescas.libraryapi.model.entity.Book;
import br.com.ruescas.libraryapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<BookDTO>> find(BookDTO dto, Pageable pageRequest) {
        Book filter = modelMapper.map(dto, Book.class);
        Page<Book> result = service.find(filter, pageRequest);
        List<BookDTO> list = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, BookDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new PageImpl<BookDTO>(list, pageRequest, result.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
        BookDTO bookDTO = service.findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(bookDTO);
    }

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody @Valid BookDTO bookDTO) {
        Book entity = modelMapper.map(bookDTO, Book.class);
        entity = service.save(entity);
        BookDTO dto = modelMapper.map(entity, BookDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) {
        return service.findById(id).map(book -> {
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book = service.update(book);
            return ResponseEntity.ok(modelMapper.map(book, BookDTO.class));
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Book book = service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(book);
        return ResponseEntity.noContent().build();
    }


}

package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.BookService;
import com.example.library.service.LogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final LogService logService;

    public BookController(BookService bookService, LogService logService) {
        this.bookService = bookService;
        this.logService = logService;
    }

    @GetMapping
    public Page<Book> list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        return bookService.list(PageRequest.of(page, size));
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam("q") String q) {
        return bookService.searchByTitle(q);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        return bookService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<Book> create(@RequestBody Book book, Principal principal) {
        Book saved = bookService.create(book);
        logService.createLog(principal.getName(), "CREATE_BOOK", "Book", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book, Principal principal) {
        Book updated = bookService.update(id, book);
        logService.createLog(principal.getName(), "UPDATE_BOOK", "Book", updated.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        bookService.delete(id);
        logService.createLog(principal.getName(), "DELETE_BOOK", "Book", id);
        return ResponseEntity.noContent().build();
    }
}

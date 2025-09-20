package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book create(Book book) {
        validateCopies(book.getTotalCopies(), book.getAvailableCopies());
        return bookRepo.save(book);
    }

    public Optional<Book> getById(Long id) {
        return bookRepo.findById(id);
    }

    public Page<Book> list(Pageable pageable) {
        return bookRepo.findAll(pageable);
    }

    public List<Book> searchByTitle(String q) {
        return bookRepo.findByTitleContainingIgnoreCase(q);
    }

    @Transactional
    public Book update(Long id, Book updated) {
        Book b = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        if (updated.getTitle() != null) b.setTitle(updated.getTitle());
        if (updated.getLanguage() != null) b.setLanguage(updated.getLanguage());
        if (updated.getPublishYear() != null) b.setPublishYear(updated.getPublishYear());
        if (updated.getIsbn() != null) b.setIsbn(updated.getIsbn());
        if (updated.getEdition() != null) b.setEdition(updated.getEdition());
        if (updated.getSummary() != null) b.setSummary(updated.getSummary());
        if (updated.getCoverImageUrl() != null) b.setCoverImageUrl(updated.getCoverImageUrl());
        if (updated.getTotalCopies() != null) b.setTotalCopies(updated.getTotalCopies());
        if (updated.getAvailableCopies() != null) b.setAvailableCopies(updated.getAvailableCopies());
        if (updated.getPublisher() != null) b.setPublisher(updated.getPublisher());
        if (updated.getAuthors() != null && !updated.getAuthors().isEmpty()) b.setAuthors(updated.getAuthors());
        if (updated.getCategories() != null && !updated.getCategories().isEmpty()) b.setCategories(updated.getCategories());

        validateCopies(b.getTotalCopies(), b.getAvailableCopies());
        return bookRepo.save(b);
    }

    @Transactional
    public void delete(Long id) {
        if (!bookRepo.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id " + id);
        }
        bookRepo.deleteById(id);
    }

    private void validateCopies(Integer total, Integer available) {
        if (total != null && available != null && available > total) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies");
        }
    }
}

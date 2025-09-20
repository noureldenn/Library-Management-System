package com.example.library.controller;

import com.example.library.entity.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorRepository authorRepo;

    public AuthorController(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @GetMapping
    public List<Author> getAll() {
        return authorRepo.findAll();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        return authorRepo.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @PostMapping
    public Author create(@RequestBody Author author) {
        return authorRepo.save(author);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, @RequestBody Author author) {
        Author existing = authorRepo.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        existing.setName(author.getName());
        return authorRepo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorRepo.deleteById(id);
    }
}

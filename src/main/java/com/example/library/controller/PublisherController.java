package com.example.library.controller;

import com.example.library.entity.Publisher;
import com.example.library.repository.PublisherRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherRepository publisherRepo;

    public PublisherController(PublisherRepository publisherRepo) {
        this.publisherRepo = publisherRepo;
    }

    @GetMapping
    public List<Publisher> getAll() {
        return publisherRepo.findAll();
    }

    @GetMapping("/{id}")
    public Publisher getById(@PathVariable Long id) {
        return publisherRepo.findById(id).orElseThrow(() -> new RuntimeException("Publisher not found"));
    }

    @PostMapping
    public Publisher create(@RequestBody Publisher publisher) {
        return publisherRepo.save(publisher);
    }

    @PutMapping("/{id}")
    public Publisher update(@PathVariable Long id, @RequestBody Publisher publisher) {
        Publisher existing = publisherRepo.findById(id).orElseThrow(() -> new RuntimeException("Publisher not found"));
        existing.setName(publisher.getName());
        existing.setAddress(publisher.getAddress());
        return publisherRepo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        publisherRepo.deleteById(id);
    }
}

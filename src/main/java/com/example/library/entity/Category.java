package com.example.library.entity;

import jakarta.persistence.*;

import jakarta.persistence.Id;

@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    // one category can have many child
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Category parent;

    //setter gettter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}

package com.example.library.entity;

import jakarta.persistence.*;

@Entity

@Table(name="roles")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // System users (staff, librarians, administrators) with role-based access control
    @Column(unique = true ,nullable = false)

    private String role;

    @Column
    private String description;
    public Role() {
    }
    public Role(String role, String description) {
        this.role = role;
    }

    public Role(String role){
        this.role = role;
    }

    //getter and setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

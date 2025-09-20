package com.example.library.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   //each member can make more than borrow
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    //each book can has more than borrow
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    //borrow date
    @Column(nullable = false)
    private LocalDate borrowDate;
    //borrow due date
    @Column(nullable = false)
    private LocalDate dueDate;
     //real return date time
    private LocalDate returnDate;

    @Column(nullable = false)
    //borrowed or returned
    private String status;
    public BorrowRecord() {}

    //  Getters  Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

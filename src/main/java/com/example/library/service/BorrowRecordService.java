package com.example.library.service;

import com.example.library.entity.BorrowRecord;
import com.example.library.entity.Book;
import com.example.library.entity.Member;
import com.example.library.repository.BorrowRecordRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository,
                               BookRepository bookRepository,
                               MemberRepository memberRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }


    public BorrowRecord borrowBook(Long memberId, Long bookId, int daysLoan) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (memberOpt.isEmpty() || bookOpt.isEmpty()) {
            throw new RuntimeException("Member or Book not found");
        }

        BorrowRecord record = new BorrowRecord();
        record.setMember(memberOpt.get());
        record.setBook(bookOpt.get());
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(daysLoan));
        record.setStatus("BORROWED");

        return borrowRecordRepository.save(record);
    }


    public BorrowRecord returnBook(Long recordId) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        record.setReturnDate(LocalDate.now());
        record.setStatus("RETURNED");

        return borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getAllRecords() {
        return borrowRecordRepository.findAll();
    }

    public Optional<BorrowRecord> getRecordById(Long id) {
        return borrowRecordRepository.findById(id);
    }
}

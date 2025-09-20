package com.example.library.controller;

import com.example.library.entity.BorrowRecord;
import com.example.library.service.BorrowRecordService;
import com.example.library.service.LogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/borrow")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;
    private final LogService logService;

    public BorrowRecordController(BorrowRecordService borrowRecordService, LogService logService) {
        this.borrowRecordService = borrowRecordService;
        this.logService = logService;
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public BorrowRecord borrowBook(@RequestParam Long memberId,
                                   @RequestParam Long bookId,
                                   @RequestParam int daysLoan,
                                   Principal principal) {
        BorrowRecord record = borrowRecordService.borrowBook(memberId, bookId, daysLoan);
        logService.createLog(principal.getName(), "BORROW_BOOK", "BorrowRecord", record.getId());
        return record;
    }

    @PostMapping("/return")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public BorrowRecord returnBook(@RequestParam Long recordId, Principal principal) {
        BorrowRecord record = borrowRecordService.returnBook(recordId);
        logService.createLog(principal.getName(), "RETURN_BOOK", "BorrowRecord", record.getId());
        return record;
    }
}

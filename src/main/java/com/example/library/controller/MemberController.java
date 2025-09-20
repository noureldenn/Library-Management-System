package com.example.library.controller;

import com.example.library.entity.Member;
import com.example.library.service.LogService;
import com.example.library.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final LogService logService;

    public MemberController(MemberService memberService, LogService logService) {
        this.memberService = memberService;
        this.logService = logService;
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public Member addMember(@RequestBody Member member, Principal principal) {
        Member saved = memberService.addMember(member);
        logService.createLog(principal.getName(), "CREATE_MEMBER", "Member", saved.getId());
        return saved;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public Member updateMember(@PathVariable Long id, @RequestBody Member member, Principal principal) {
        Member updated = memberService.updateMember(id, member);
        logService.createLog(principal.getName(), "UPDATE_MEMBER", "Member", updated.getId());
        return updated;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMember(@PathVariable Long id, Principal principal) {
        memberService.deleteMember(id);
        logService.createLog(principal.getName(), "DELETE_MEMBER", "Member", id);
    }
}

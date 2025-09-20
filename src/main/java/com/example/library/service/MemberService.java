package com.example.library.service;

import com.example.library.entity.Member;
import com.example.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepo;

    public MemberService(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepo.findById(id);
    }

    public Member addMember(Member member) {
        return memberRepo.save(member);
    }

    public Member updateMember(Long id, Member member) {
        return memberRepo.findById(id)
                .map(existing -> {
                    existing.setFullName(member.getFullName());
                    existing.setEmail(member.getEmail());
                    existing.setPhone(member.getPhone());
                    existing.setAddress(member.getAddress());
                    existing.setActive(member.getActive());
                    return memberRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public void deleteMember(Long id) {
        memberRepo.deleteById(id);
    }
}

package com.club.closed_club.service;

import com.club.closed_club.entity.IMemberRepository;
import com.club.closed_club.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final IMemberRepository repository;

    public MemberService(IMemberRepository repository) {
        this.repository = repository;
    }

    public Optional<Member> findMemberById(Long id) {
        return repository.findById(id);
    }

    public List<Member> findMemberByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    public Optional<Member> findMemberByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Member createMember(Member member) {
        return repository.save(member);
    }

    public void deleteMember(Long id) {
        repository.deleteById(id);
    }

    public Optional<Member> putMember (Long id, Member member) {
        return repository.findById(id)
                .map(existingMember -> {
                   existingMember.setFirstName(member.getFirstName());
                   existingMember.setLastName(member.getLastName());
                   existingMember.setMiddleName(member.getMiddleName());
                    return existingMember;
                });
    }

    public List<Member> findAll () {
        return repository.findAll();
    }


}

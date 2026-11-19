package com.club.closed_club.service;

import com.club.closed_club.dto.MemberMapper;
import com.club.closed_club.dto.MemberRequest;
import com.club.closed_club.dto.MemberResponse;
import com.club.closed_club.entity.IMemberRepository;
import com.club.closed_club.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final IMemberRepository repository;

    public MemberService(IMemberRepository repository) {
        this.repository = repository;
    }

    public MemberResponse findMemberById(Long id) {
        Member member = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
        return MemberMapper.toResponse(member);

    }

    public List<Member> findMemberByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    public Optional<Member> findMemberByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Member createMember(MemberRequest memberRequest) {
        Member member = new Member(memberRequest.getFirstName(), memberRequest.getLastName(), memberRequest.getMiddleName());
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

    public Page<Member> findAll (Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Member> memberPage (String lastName, Pageable pageable) {
        return repository.findByLastNameContainingIgnoreCase(lastName, pageable);
    }

    public Page<MemberResponse> getAllMembers(String lastName, Pageable pageable){
        Page<Member> members;
        if (lastName == null || lastName.isBlank()) {
            members = repository.findAll(pageable);
        } else  {
            members = repository.findByLastNameContainingIgnoreCase(lastName, pageable);
        }
        Page<MemberResponse> memberResponses = members
                .map(m -> MemberMapper.toResponse(m));


        return  memberResponses;
    }
}

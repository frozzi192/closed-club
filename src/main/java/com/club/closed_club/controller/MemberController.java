package com.club.closed_club.controller;

import com.club.closed_club.dto.MemberMapper;
import com.club.closed_club.dto.MemberRequest;
import com.club.closed_club.dto.MemberResponse;
import com.club.closed_club.entity.IMemberRepository;
import com.club.closed_club.entity.Member;
import com.club.closed_club.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById (@PathVariable Long id) {
        Member member = memberService.findMemberById(id);
        MemberResponse memberResponse = MemberMapper.toResponse(member);
        return ResponseEntity.ok(memberResponse);


    }


    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest memberRequest) {
        Member savedMember = memberService.createMember(memberRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MemberMapper.toResponse(savedMember));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Member> deleteMember (@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> putMember (@PathVariable Long id, @RequestBody Member member) {
        Optional<Member> updatedMember = memberService.putMember(id, member);
        return updatedMember
                .map(m -> ResponseEntity.ok(m))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
    @GetMapping
    public Page<MemberResponse> getAllMembers(@RequestParam(required = false) String lastName, Pageable pageable) {
        Page<Member> member;
        if (lastName == null || lastName.isBlank()) {
            member = memberService.findAll(pageable);
        } else {
            member = memberService.memberPage(lastName, pageable);
        }
        Page<MemberResponse> memberResponses = member
                .map(m -> MemberMapper.toResponse(m));

        return memberResponses;
    }





}
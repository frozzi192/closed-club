package com.club.closed_club.controller;

import com.club.closed_club.dto.MemberMapper;
import com.club.closed_club.dto.MemberRequest;
import com.club.closed_club.dto.MemberResponse;
import com.club.closed_club.entity.IMemberRepository;
import com.club.closed_club.entity.Member;
import com.club.closed_club.service.MemberService;
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
    public ResponseEntity<MemberResponse> responseEntity (@PathVariable Long id) {
        Optional<Member> optionalMember= memberService.findMemberById(id);
        return optionalMember
                .map(m -> ResponseEntity.ok(MemberMapper.toResponse(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest memberRequest) {
        Member member = new Member(memberRequest.getFirstName(), memberRequest.getLastName(), memberRequest.getMiddleName());
        Member savedMember = memberService.createMember(member);
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
    public List<MemberResponse> responseEntity() {
        List<Member> member = memberService.findAll();
        List<MemberResponse> memberResponses = member.stream()
                .map(m -> MemberMapper.toResponse(m))
                .toList();

        return memberResponses;
    }





}
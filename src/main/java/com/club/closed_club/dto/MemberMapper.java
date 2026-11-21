package com.club.closed_club.dto;

import com.club.closed_club.entity.Member;
import org.springframework.http.ResponseEntity;

public class MemberMapper {

    public static MemberResponse toResponse(Member member) {
         MemberResponse memberResponse = new MemberResponse(member.getId(), member.getFirstName(), member.getLastName(), member.getMiddleName());
         return memberResponse;
    }

}

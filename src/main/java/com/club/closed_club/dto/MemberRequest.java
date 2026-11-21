package com.club.closed_club.dto;

import jakarta.validation.constraints.NotBlank;

public class MemberRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    private String middleName;

    public MemberRequest(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

}
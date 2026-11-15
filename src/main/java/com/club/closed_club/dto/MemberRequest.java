package com.club.closed_club.dto;

public class MemberRequest {

    private String firstName;
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
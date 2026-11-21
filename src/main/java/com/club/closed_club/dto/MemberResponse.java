package com.club.closed_club.dto;

public class MemberResponse {

private Long id;
private String firstName;
private String lastName;
private  String middleName;

    public MemberResponse(Long id, String firstName, String lastName, String middleName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public Long getId() {
        return id;
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

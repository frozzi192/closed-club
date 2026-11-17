package com.club.closed_club.entity;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMemberRepository extends JpaRepository <Member, Long>{


    Optional<Member> findByFirstNameAndLastName(
        String firstName,
        String lastName
    );

    List<Member> findByLastName(
        String lastName
    );

    Optional<Member> findByFirstNameAndLastNameAndMiddleName(
            String firstName,
            String lastName,
            String middleName

    );

    Page<Member> findByLastNameContainingIgnoreCase(
            String lastName,
            Pageable pageable
    );


}

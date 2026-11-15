package com.club.closed_club.entity;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "qr_codes")
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    public QrCode(Long id, UUID uuid, Member member) {
        this.id = id;
        this.uuid = uuid;
        this.member = member;
    }

    public QrCode(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

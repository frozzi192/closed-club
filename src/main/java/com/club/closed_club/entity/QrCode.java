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
    private UUID qrToken;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    public QrCode(UUID qrToken, Member member) {
        this.qrToken = qrToken;
        this.member = member;
    }
    public QrCode() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getQrToken() {
        return qrToken;
    }

    public void setQrToken(UUID qrToken) {
        this.qrToken = qrToken;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

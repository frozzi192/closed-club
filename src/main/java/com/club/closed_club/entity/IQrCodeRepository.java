package com.club.closed_club.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IQrCodeRepository extends JpaRepository <QrCode, Long>{
    Optional<QrCode> findByQrToken(UUID qrToken);
}

package com.club.closed_club.controller;


import com.club.closed_club.entity.Member;
import com.club.closed_club.entity.QrCode;
import com.club.closed_club.service.QrCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping ("/api/qr-codes")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/{memberId}")
    public QrCode createQrCode (@PathVariable Long memberId)  {
        QrCode qrCode = qrCodeService.createQrCode(memberId);
        return qrCode;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<QrCode> findByQrCode (@PathVariable UUID uuid) {
        Optional<QrCode> qrCode = qrCodeService.findByQr(uuid);
         return qrCode
                .map(q -> ResponseEntity.ok(q))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQrCode (@PathVariable Long id) {
        qrCodeService.deleteQrCode(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<QrCode>> allQrCode() {
        List<QrCode> qrCode = qrCodeService.allQrCodes();
        return ResponseEntity.ok(qrCode);
    }

    @PostMapping("/use/{uuid}")
    public ResponseEntity<Member> scanQrCode (@PathVariable UUID uuid) {
        Optional<Member> qrCodeMember = qrCodeService.useQrCode(uuid);

        return qrCodeMember
                .map(m -> ResponseEntity.ok(m))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}

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

    @GetMapping("/{id}")
    public ResponseEntity<QrCode> findByQrCode (@PathVariable UUID id) {
        Optional<QrCode> qrCode = qrCodeService.findByQr(id);
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

    @PostMapping("/use/{qrToken}")
    public ResponseEntity<Member> scanQrCode (@PathVariable UUID qrToken) {
        Optional<Member> qrCodeMember = qrCodeService.useQrCode(qrToken);

        return qrCodeMember
                .map(m -> ResponseEntity.ok(m))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}

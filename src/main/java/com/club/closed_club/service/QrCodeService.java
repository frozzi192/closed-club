package com.club.closed_club.service;

import com.club.closed_club.entity.IMemberRepository;
import com.club.closed_club.entity.IQrCodeRepository;
import com.club.closed_club.entity.Member;
import com.club.closed_club.entity.QrCode;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QrCodeService {
    private final IMemberRepository repositoryMember;
    private final IQrCodeRepository repository;

    public QrCodeService(IMemberRepository repositoryMember, IQrCodeRepository repository) {
        this.repositoryMember = repositoryMember;
        this.repository = repository;
    }

    public Optional<QrCode> findByQr (UUID uuid) {
        Optional<QrCode> qrCode = repository.findByUuid(uuid);
        return qrCode;
    }

    public QrCode createQrCode (Long memberID) {
        QrCode qrCode = new QrCode();
        UUID uuid = UUID.randomUUID();
        qrCode.setUuid(uuid);
        Optional<Member> optional = repositoryMember.findById(memberID);

        Member member = optional.orElseThrow(() -> new RuntimeException("Member not found"));
        qrCode.setMember(member);
        return repository.save(qrCode);

    }

    public void deleteQrCode(Long id) {
        repository.deleteById(id);
    }

    public List<QrCode> allQrCodes () {
        return repository.findAll();
    }

    @Transactional
    public Optional<Member> useQrCode (UUID uuid) {
         Optional<QrCode> qrCodeOptional = repository.findByUuid(uuid);

         if (qrCodeOptional.isEmpty()) {
             return Optional.empty();
         }

         QrCode qrCode = qrCodeOptional.get();

         UUID newUuid = UUID.randomUUID();
         qrCode.setUuid(newUuid);
         repository.save(qrCode);

         return Optional.of(qrCode.getMember());
    }

}

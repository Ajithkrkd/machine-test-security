package com.ajith.security.aop.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;

    public void saveAuditData (AuditData data) {
        auditRepository.save ( data );
    }

    public ResponseEntity<List< AuditData> > getAllLog ( ) {
       return ResponseEntity.status ( HttpStatus.OK ).body ( auditRepository.findAll ());
    }
}

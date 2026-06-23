package com.example.simplearticle.services;

import com.example.simplearticle.models.AuditLog;
import com.example.simplearticle.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void save(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }
}

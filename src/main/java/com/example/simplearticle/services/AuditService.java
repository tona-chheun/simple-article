package com.example.simplearticle.services;

import com.example.simplearticle.models.AuditLog;

public interface AuditService {
    void save(AuditLog auditLog);
}

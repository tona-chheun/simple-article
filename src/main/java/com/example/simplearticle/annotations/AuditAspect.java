package com.example.simplearticle.annotations;

import com.example.simplearticle.models.AuditLog;
import com.example.simplearticle.services.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;
    //private final ObjectMapper objectMapper;
    private final JsonMapper jsonMapper;

    public AuditAspect(AuditService auditService,
                       ObjectMapper objectMapper,
                       JsonMapper jsonMapper
    ) {
        this.auditService = auditService;
        // this.objectMapper = objectMapper;
        this.jsonMapper = jsonMapper;
    }

    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {

        AuditLog auditLog = new AuditLog();
        auditLog.setAction(auditable.action());
        auditLog.setModuleName(auditable.module());
        auditLog.setMethodName(joinPoint.getSignature().getName());
        auditLog.setCreatedAt(LocalDateTime.now());

        try {
            String requestData = jsonMapper.writeValueAsString(joinPoint.getArgs());
            auditLog.setNewData(requestData);

            Object result = joinPoint.proceed();

            auditLog.setStatus("SUCCESS");
            auditService.save(auditLog);

            return result;

        } catch (Exception ex) {
            auditLog.setStatus("FAILED");
            auditLog.setErrorMessage(ex.getMessage());

            auditService.save(auditLog);

            throw ex;
        }
    }
}

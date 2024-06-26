package com.ajith.security.aop.audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int auditId;
    private String methodType;
    private String apiEndpoint;
    @Lob
    private String request;
    @Lob
    private String response;
    private String userInvoked;
    private String requestTime;
}

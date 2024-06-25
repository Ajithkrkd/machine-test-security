package com.ajith.security.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private String message;
    private String description;
    private LocalDateTime timestamp;
}

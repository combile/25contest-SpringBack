package com.example.newai.appuser.vo;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
public class UserRequest {
    private String username;
    private String phoneNumber;
    private String email;
    private String vocabularyLevel;
    private String loginId;
    private String password;
}

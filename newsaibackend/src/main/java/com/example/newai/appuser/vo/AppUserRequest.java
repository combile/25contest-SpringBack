package com.example.newai.appuser.vo;

import lombok.Data;

@Data
public class AppUserRequest {
    private String username;
    private String phoneNumber;
    private String email;
    private String vocabularyLevel;
    private String loginId;
    private String password;
}

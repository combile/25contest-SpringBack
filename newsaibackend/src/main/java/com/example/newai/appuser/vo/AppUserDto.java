package com.example.newai.appuser.vo;

import com.example.newai.member.vo.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppUserDto {
    private Long appUserId;
    private String username;
    private String phoneNumber;
    private String email;
    private Level vocabularyLevel;
    private MemberDto member;
}

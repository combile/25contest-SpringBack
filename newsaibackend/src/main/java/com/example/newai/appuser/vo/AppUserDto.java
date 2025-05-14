package com.example.newai.appuser.vo;

import com.example.newai.member.vo.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private Long appUserId;
    private String username;
    private String phoneNumber;
    private String email;
    private Level vocabularyLevel;
    private Integer views;
    private MemberDto member;
}

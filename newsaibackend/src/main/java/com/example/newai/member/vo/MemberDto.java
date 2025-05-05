package com.example.newai.member.vo;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.vo.AppUserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String loginId;
    private String password;
    private AppUserDto appUserDto;
}

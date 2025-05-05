package com.example.newai.appuser.controller;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.repository.AppUserRepository;
import com.example.newai.appuser.vo.AppUserDto;
import com.example.newai.appuser.vo.Level;
import com.example.newai.appuser.vo.UserRequest;

import com.example.newai.member.entity.Member;
import com.example.newai.member.repository.MemberRepository;
import com.example.newai.member.vo.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app-user")
@RequiredArgsConstructor
public class AppUserController {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/test/creation")
    public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest) {
        AppUser appUser = new AppUser();
        appUser.setUsername(userRequest.getUsername());
        appUser.setPhoneNumber(userRequest.getPhoneNumber());
        appUser.setEmail(userRequest.getEmail());
        appUser.setVocabularyLevel(Level.valueOf(userRequest.getVocabularyLevel()));

        Member member = new Member();
        member.setLoginId(userRequest.getLoginId());
        member.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        appUser.setMember(member);
        member.setAppUser(appUser);

        appUserRepository.save(appUser);
        memberRepository.save(member);

        AppUserDto appUserDto = new AppUserDto(
        appUser.getAppUserId(),
        appUser.getUsername(),
        appUser.getPhoneNumber(),
        appUser.getEmail(),
        appUser.getVocabularyLevel(),
        new MemberDto(member.getMemberId(), member.getLoginId(), member.getPassword(), null)
    );

        return new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
    }
}

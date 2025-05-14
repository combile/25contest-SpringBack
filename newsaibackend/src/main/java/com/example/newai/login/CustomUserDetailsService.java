package com.example.newai.login;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.repository.AppUserRepository;
import com.example.newai.member.entity.Member;
import com.example.newai.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId);

        if (member == null) {
            throw new UsernameNotFoundException("회원을 찾을 수 없습니다: " + loginId);
        }

        AppUser appUser = member.getAppUser();
        if (appUser == null) {
            throw new UsernameNotFoundException("AppUser 정보가 없습니다: " + loginId);
        }

        return new CustomUserDetails(appUser);
    }
}

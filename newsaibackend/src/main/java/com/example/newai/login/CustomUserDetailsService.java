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
        try {
            Member member = memberRepository.findByLoginId(loginId);
            AppUser appUser = member.getAppUser();
            return new CustomUserDetails(appUser);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}

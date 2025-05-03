package com.example.newai.login;

import com.example.newai.appuser.entity.AppUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private final Long appUserId;
    private final String loginId;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(AppUser appUser) {
        this.appUserId = appUser.getAppUserId();
        this.loginId = appUser.getMember().getLoginId();
        this.password = appUser.getMember().getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return loginId;
    }
}

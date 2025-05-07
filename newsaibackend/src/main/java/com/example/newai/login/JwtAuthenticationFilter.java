package com.example.newai.login;

import com.example.newai.login.vo.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

     public JwtAuthenticationFilter(AuthenticationManager authManager,
                                   JwtUtil jwtUtil,
                                   RefreshTokenService refreshSvc) {
        this.authenticationManager = authManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshSvc;

        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String loginId = ((CustomUserDetails) authResult.getPrincipal()).getLoginId();
        String accessToken = jwtUtil.generateAccessToken(loginId);
        String refreshToken = jwtUtil.generateRefreshToken(loginId);

        refreshTokenService.createOrUpdateToken(loginId, refreshToken);

        response.setHeader("Authorization", "Bearer " + accessToken);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}

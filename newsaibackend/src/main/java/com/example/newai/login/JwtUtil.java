package com.example.newai.login;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class JwtUtil {
    @Value("${jwt.access.secret.key}")
    private String ACCESS_SECRET_KEY;
    @Value("${jwt.refresh.secret.key}")
    private String REFRESH_SECRET_KEY;
    private final Long ACCESS_EXP = Duration.ofMinutes(1).toMillis();
    private final Long REFRESH_EXP = Duration.ofDays(7).toMillis();

    public String generateAccessToken(String loginId) {
        Date now = new Date();

        return Jwts.builder()
                .subject(loginId)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + ACCESS_EXP))
                .signWith(Keys.hmacShaKeyFor(ACCESS_SECRET_KEY.getBytes()))
                .compact();
    }

    public String extractAccessTokenLoginId(String accessToken) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(ACCESS_SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getSubject();
    }

    public boolean validateAccessToken(String accessToken, CustomUserDetails customUserDetails) {
        String loginId = extractAccessTokenLoginId(accessToken);
        Date exp = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(ACCESS_SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getExpiration();
         return loginId.equals(customUserDetails.getLoginId()) && exp.after(new Date());
    }

    public boolean isAccessTokenExpired(String accessToken) {
        Date exp = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(ACCESS_SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getExpiration();
        return exp.before(new Date());
    }

    public String generateRefreshToken(String loginId) {
        Date now = new Date();

        return Jwts.builder()
                .subject(loginId)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + ACCESS_EXP))
                .signWith(Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes()))
                .compact();
    }

    public String extractRefreshTokenLoginId(String refreshToken) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getSubject();
    }

    // 리프레시 토큰은 항상 서버에서 관리하기 때문에 조작 위협은 적다. (서버가 해킹당하지 않는 이상)
    // 따라서 굳이 유효성검사 함수를 빼기보단 기간 만료 되었는지 먼저 확인하자.
    public boolean isRefreshTokenExpired(String refreshToken) {
        Date exp = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getExpiration();
        return exp.before(new Date());
    }
}

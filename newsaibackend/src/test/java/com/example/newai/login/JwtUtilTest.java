package com.example.newai.login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {
    @Autowired
    JwtUtil jwtUtil = new JwtUtil();


    @Test
    void getACCESS_SECRET_KEY() {

        String jwt = jwtUtil.getACCESS_SECRET_KEY();
        System.out.println(jwt);
        //Assertions.assertNotNull(jwt);
    }

    @Test
    void generateAndParseAccessToken() {
        String loginId = "testUser";
        String token = jwtUtil.generateAccessToken(loginId);

        // 토큰 유효 테스트
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // 토큰 추출 테스트
        String extracted = jwtUtil.extractAccessTokenLoginId(token);
        assertEquals(loginId, extracted);
    }

    @Test
    void checkAccessTokenNotExpired() {
        String token = jwtUtil.generateAccessToken("testUser");

        // 토큰 만료 테스트
        boolean expired = jwtUtil.isAccessTokenExpired(token);
        assertFalse(expired, "AccessToken이 생성 직후인데 만료되면 안 됨");
    }

    @Test
    void generateAndParseRefreshToken() {
        String loginId = "testUser";
        String token = jwtUtil.generateRefreshToken(loginId);

        // 토큰 유효 테스트
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // 토큰 추출 테스트
        String extracted = jwtUtil.extractRefreshTokenLoginId(token);
        assertEquals(loginId, extracted);
    }

    @Test
    void checkRefreshTokenNotExpired() {
        String token = jwtUtil.generateRefreshToken("testUser");

        // 토큰 만료 테스트
        boolean expired = jwtUtil.isRefreshTokenExpired(token);
        assertFalse(expired, "RefreshToken이 생성 직후인데 만료되면 안 됨");
    }
}
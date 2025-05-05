package com.example.newai.login;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.repository.AppUserRepository;
import com.example.newai.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserRepository appUserRepository;
    private final MemberRepository memberRepository;

    public void createOrUpdateToken(String loginId, String refreshToken) {
        AppUser appUser = appUserRepository.findByAppUserId(memberRepository.findByLoginId(loginId).getAppUser().getAppUserId());
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByAppUser(appUser);

        if (optionalRefreshToken.isEmpty()) {
            RefreshToken newRefreshToken = new RefreshToken();
            newRefreshToken.setAppUser(appUser);
            newRefreshToken.setRefreshToken(refreshToken);
            newRefreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

            refreshTokenRepository.save(newRefreshToken);
        } else {
            optionalRefreshToken.get().setRefreshToken(refreshToken);
            optionalRefreshToken.get().setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

            refreshTokenRepository.save(optionalRefreshToken.get());
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        RefreshToken checkRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);

        if (checkRefreshToken == null)
            return false;

        return checkRefreshToken.getExpiryDate().isAfter(Instant.now());
    }
}

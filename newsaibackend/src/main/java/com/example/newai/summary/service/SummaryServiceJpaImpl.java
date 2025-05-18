package com.example.newai.summary.service;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.repository.AppUserRepository;
import com.example.newai.appuser.vo.Level;
import com.example.newai.login.CustomUserDetails;
import com.example.newai.member.entity.Member;
import com.example.newai.member.repository.MemberRepository;
import com.example.newai.news.entity.News;
import com.example.newai.news.repository.NewsRepository;
import com.example.newai.summary.entity.Summary;
import com.example.newai.summary.repository.SummaryRepository;
import com.example.newai.summary.vo.SummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SummaryServiceJpaImpl implements SummaryService {
    private final AppUserRepository appUserRepository;
    private final MemberRepository memberRepository;
    private final NewsRepository newsRepository;
    private final SummaryRepository summaryRepository;

    @Override
    public SummaryDto readSummary(Authentication authentication, UUID uuid) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        News news = newsRepository.findByUuid(uuid);

        if (news==null)
            return null;

        AppUser appUser = authAppUser.get();
        Summary summary;

        if (appUser.getVocabularyLevel() == Level.LOW || appUser.getVocabularyLevel() == Level.MIDDLE) {
            summary = summaryRepository.findByNewsAndLevel(news, Level.LOW);
        } else {
            summary = summaryRepository.findByNewsAndLevel(news, Level.HIGH);
        }

        if (summary==null)
            return null;

        return summaryToSummaryDto(summary);
    }

    public static SummaryDto summaryToSummaryDto(Summary summary) {
        return new SummaryDto(summary.getSummaryId(), summary.getLevel(), summary.getSummaryContent());
    }

    private Optional<AppUser> authenticationToAppUser(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (customUserDetails == null) {
            return Optional.empty();
        }

        Member member = memberRepository.findByLoginId(customUserDetails.getLoginId());
        return appUserRepository.findById(member.getAppUser().getAppUserId());
    }
}

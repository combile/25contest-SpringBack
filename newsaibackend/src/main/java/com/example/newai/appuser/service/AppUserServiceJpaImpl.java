package com.example.newai.appuser.service;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.repository.AppUserRepository;
import com.example.newai.appuser.vo.AppUserDto;
import com.example.newai.appuser.vo.AppUserRequest;
import com.example.newai.appuser.vo.Level;
import com.example.newai.appuser.vo.RedundancyCheckRequest;
import com.example.newai.login.CustomUserDetails;
import com.example.newai.member.entity.Member;
import com.example.newai.member.repository.MemberRepository;
import com.example.newai.member.vo.MemberDto;
import com.example.newai.news.entity.News;
import com.example.newai.news.repository.NewsRepository;
import com.example.newai.news.service.NewsService;
import com.example.newai.news.service.NewsServiceJpaImpl;
import com.example.newai.news.vo.NewsDto;
import com.example.newai.word.entity.Word;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserServiceJpaImpl implements AppUserService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final MemberRepository memberRepository;
    private final NewsRepository newsRepository;

    @Transactional
    @Override
    public AppUserDto createAppUser(AppUserRequest appUserRequest) {
        AppUser appUser = new AppUser();
        appUser.setUsername(appUserRequest.getUsername());
        appUser.setPhoneNumber(appUserRequest.getPhoneNumber());
        appUser.setEmail(appUserRequest.getEmail());
        appUser.setVocabularyLevel(Level.valueOf(appUserRequest.getVocabularyLevel()));
        appUser.setViews(0);

        Member member = new Member();
        member.setLoginId(appUserRequest.getLoginId());
        member.setPassword(passwordEncoder.encode(appUserRequest.getPassword()));

        appUser.setMember(member);
        member.setAppUser(appUser);

        appUserRepository.save(appUser);
        memberRepository.save(member);

        AppUserDto appUserDto = appUserToAppUserDto(appUser);

        return appUserDto;
    }

    @Override
    public AppUserDto getAppUser(Authentication authentication) {
        Optional<AppUser> appUser = authenticationToAppUser(authentication);

        if (appUser.isEmpty())
            return null;

        AppUserDto appUserDto = appUserToAppUserDto(appUser.get());

        return appUserDto;
    }

    @Override
    @Transactional
    public AppUserDto updateAppUser(Authentication authentication, AppUserRequest appUserRequest) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        AppUser appUser = authAppUser.get();

        if (appUserRequest.getUsername() != null) {
            appUser.setUsername(appUserRequest.getUsername());
        }
        if (appUserRequest.getPhoneNumber() != null) {
            appUser.setPhoneNumber(appUserRequest.getPhoneNumber());
        }
        if (appUserRequest.getEmail() != null) {
            appUser.setEmail(appUserRequest.getEmail());
        }
        if (appUserRequest.getVocabularyLevel() != null) {
            appUser.setVocabularyLevel(Level.valueOf(appUserRequest.getVocabularyLevel()));
        }

        appUser = appUserRepository.save(appUser);

        AppUserDto appUserDto = appUserToAppUserDto(appUser);

        return appUserDto;
    }

    @Override
    @Transactional
    public void deleteAppUser(Authentication authentication) {
        Optional<AppUser> appUser = authenticationToAppUser(authentication);
        appUser.ifPresent(appUserRepository::delete);
    }

    // Field Method
    @Override
    @Transactional
    public void updateAppUserViews(Authentication authentication) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return;

        AppUser appUser = authAppUser.get();
        appUser.setViews(appUser.getViews() + 1);

        appUserRepository.save(appUser);
    }

    @Override
    @Transactional
    public void addSeenWords(Authentication authentication, List<Word> words) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return;

        AppUser appUser = authAppUser.get();

        for (Word word : words) {
            appUser.getSeenWords().add(word);
            word.getAppUsers().add(appUser);
        }

        appUserRepository.save(appUser);
    }
    // Field Method

    // NewsBookmark Method
    @Override
    @Transactional
    public NewsDto newsBookmark(Authentication authentication, UUID uuid) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        AppUser appUser = authAppUser.get();

        News news = newsRepository.findByUuid(uuid);

        if (news == null)
            return null;

        if (!appUser.getBookmarks().contains(news)) {
            appUser.getBookmarks().add(news);
            news.getAppUsers().add(appUser);

            appUserRepository.save(appUser);
            newsRepository.save(news);
        }

        return NewsServiceJpaImpl.newsToNewsDto(news);
    }

    @Override
    public List<NewsDto> readAllBookmark(Authentication authentication) {
        List<NewsDto> newsDtos = new ArrayList<>();
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        AppUser appUser = authAppUser.get();

        for (News news : appUser.getBookmarks()) {
            newsDtos.add(NewsServiceJpaImpl.newsToNewsDto(news));
        }

        return  newsDtos;
    }

    @Override
    @Transactional
    public void deleteNewsBookmark(Authentication authentication, UUID uuid) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return;

        AppUser appUser = authAppUser.get();

        News news = newsRepository.findByUuid(uuid);

        if (news == null)
            return;

        if (appUser.getBookmarks().contains(news)) {
            appUser.getBookmarks().remove(news);
            news.getAppUsers().remove(appUser);

            appUserRepository.save(appUser);
            newsRepository.save(news);
        }
    }
    // NewsBookmark Method


    @Override
    public Boolean redundancyCheck(RedundancyCheckRequest redundancyCheckRequest) {
        if (redundancyCheckRequest.getEmail() != null) {
            return appUserRepository.findByEmail(redundancyCheckRequest.getEmail()) != null; // true
        }

        if (redundancyCheckRequest.getLoginId() != null) {
            return memberRepository.findByLoginId(redundancyCheckRequest.getLoginId()) != null; // true
        }

        return false;
    }

    public static AppUserDto appUserToAppUserDto(AppUser appUser) {
        MemberDto memberDto = new  MemberDto(appUser.getMember().getMemberId(), appUser.getMember().getLoginId(), appUser.getMember().getPassword(), null);
        AppUserDto appUserDto = new AppUserDto(appUser.getAppUserId(), appUser.getUsername(), appUser.getPhoneNumber(), appUser.getEmail(), appUser.getVocabularyLevel(), appUser.getViews(), memberDto);

        return appUserDto;
    }

    private Optional<AppUser> authenticationToAppUser(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (customUserDetails == null) {
            return  Optional.empty();
        }

        Member member = memberRepository.findByLoginId(customUserDetails.getLoginId());
        return appUserRepository.findById(member.getAppUser().getAppUserId());
    }
}

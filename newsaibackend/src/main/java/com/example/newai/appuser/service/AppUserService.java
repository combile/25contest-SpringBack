package com.example.newai.appuser.service;

import com.example.newai.appuser.vo.AppUserDto;
import com.example.newai.appuser.vo.AppUserRequest;
import com.example.newai.appuser.vo.RedundancyCheckRequest;
import com.example.newai.news.vo.NewsDto;
import com.example.newai.word.entity.Word;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface AppUserService {
    AppUserDto createAppUser(AppUserRequest appUserRequest);
    AppUserDto getAppUser(Authentication authentication);
    AppUserDto updateAppUser(Authentication authentication, AppUserRequest appUserRequest);
    void deleteAppUser(Authentication authentication);

    // Field Service
    void updateAppUserViews(Authentication authentication);
    void addSeenWords(Authentication authentication, List<Word> words);

    // News Bookmark
    NewsDto newsBookmark(Authentication authentication, UUID uuid);
    List<NewsDto> readAllBookmark(Authentication authentication);
    void deleteNewsBookmark(Authentication authentication, UUID uuid);

    Boolean redundancyCheck(RedundancyCheckRequest redundancyCheckRequest);
}

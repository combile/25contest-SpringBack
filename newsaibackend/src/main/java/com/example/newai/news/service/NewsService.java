package com.example.newai.news.service;

import com.example.newai.news.vo.NewsDto;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    NewsDto readNews(Authentication authentication, UUID uuid);

    List<NewsDto> readAllNewsByLatest();
    List<NewsDto> readAllNewsByViews();
    List<NewsDto> searchNews(String keyword);
    List<NewsDto> searchBookmark(Authentication authentication, String keyword);
}

package com.example.newai.news.service;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.entity.QAppUser;
import com.example.newai.appuser.repository.AppUserRepository;
import com.example.newai.appuser.service.AppUserService;
import com.example.newai.domain.vo.DomainDto;
import com.example.newai.login.CustomUserDetails;
import com.example.newai.member.entity.Member;
import com.example.newai.member.repository.MemberRepository;
import com.example.newai.news.entity.News;
import com.example.newai.news.entity.QNews;
import com.example.newai.news.repository.NewsRepository;
import com.example.newai.news.vo.NewsDto;
import com.example.newai.word.entity.Word;
import com.example.newai.word.vo.WordDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceJpaImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final AppUserRepository appUserRepository;
    private final MemberRepository memberRepository;
    private final AppUserService appUserService;
    private final ModelMapper modelMapper;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public NewsDto readNews(Authentication authentication, UUID uuid) {
        News news = newsRepository.findByUuid(uuid);

        if (news==null)
            return null;

        news.setViews(news.getViews() + 1);
        newsRepository.save(news);

        // 뉴스 조회수 증가
        appUserService.updateAppUserViews(authentication);

        // 본 워드 저장
        List<Word> words = news.getWords();
        appUserService.addSeenWords(authentication, words);

        // Word도 같이 보내줘야함
        return newsToNewsDto(news);
    }

    @Override
    public List<NewsDto> readAllNewsByLatest() {
        List<NewsDto> newsDtos = new ArrayList<>();

        List<News> sortedNewsByLatest = newsRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));

        for (News news : sortedNewsByLatest) {
            newsDtos.add(newsToNewsDto(news));
        }

        return newsDtos;
    }

    @Override
    public List<NewsDto> readAllNewsByViews() {
        List<NewsDto> newsDtos = new ArrayList<>();

        List<News> sortedNewsByViews = newsRepository.findAll(Sort.by(Sort.Order.desc("views")));

        for (News news : sortedNewsByViews) {
            newsDtos.add(newsToNewsDto(news));
        }

        return newsDtos;
    }

    @Override
    public List<NewsDto> searchNews(String keyword) {
        List<NewsDto> newsDtos = new ArrayList<>();

        QNews qNews = QNews.news;
        List<News> results = jpaQueryFactory.selectFrom(qNews).where(qNews.title.containsIgnoreCase(keyword)).orderBy(qNews.createdAt.desc()).fetch();

        for (News result : results) {
            newsDtos.add(newsToNewsDto(result));
        }

        return newsDtos;
    }

    @Override
    public List<NewsDto> searchBookmark(Authentication authentication, String keyword) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        AppUser appUser = authAppUser.get();

        List<NewsDto> newsDtos = new ArrayList<>();

        QNews qNews = QNews.news;
        QAppUser qAppUser =  QAppUser.appUser;
        // 뉴스에 북마크 해간 사용자들과 사용자 테이블을 조인하고 그중에 요청한 사용자만 거르고, 그중에 남은 레코드 중에서 title에 keyword가 포함된 것들을 반환. 반환하는 대신 최신순으로 정렬
        List<News> results = jpaQueryFactory.selectFrom(qNews).join(qNews.appUsers, qAppUser).where(qAppUser.appUserId.eq(appUser.getAppUserId()) ,qNews.title.containsIgnoreCase(keyword)).orderBy(qNews.createdAt.desc()).fetch();

        for (News result : results) {
            newsDtos.add(newsToNewsDto(result));
        }

        return newsDtos;
    }

    public static NewsDto newsToNewsDto(News news) {
        List<DomainDto> domainDtos = news.getDomains().stream().map(domain -> new DomainDto(domain.getDomainId(), domain.getDomain())).toList();
        List<WordDto> wordDtos = news.getWords().stream().map(word -> new WordDto(word.getWordId(), word.getWord(), word.getDefinition(), word.getSentence(), word.getLevel())).toList();
        return new NewsDto(news.getNewsId(), news.getUuid(), news.getTitle(), news.getAuthor(), domainDtos, news.getContent(), news.getViews(), news.getThumbnailUrl(), wordDtos, news.getCreatedAt());
        //return modelMapper.map(news, NewsDto.class);
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

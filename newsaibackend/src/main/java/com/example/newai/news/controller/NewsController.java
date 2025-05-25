package com.example.newai.news.controller;

import com.example.newai.news.service.NewsService;
import com.example.newai.news.vo.NewsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Tag(name = "News", description = "뉴스관련 API")
public class NewsController {
    private final NewsService newsService;

    @Operation(
        summary = "특정 뉴스 조회",
        description = "뉴스 UUID를 통해 뉴스 상세 정보를 조회.",
        parameters = {
            @Parameter(
                in = ParameterIn.PATH,
                name = "uuid",
                description = "뉴스 UUID",
                required = true,
                example = "123e4567-e89b-12d3-a456-426614174000"
            ),
            @Parameter(
                in = ParameterIn.HEADER,
                name = HttpHeaders.AUTHORIZATION,
                required = true,
                description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}"
            )
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "뉴스 조회 성공",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))),
            @ApiResponse(responseCode = "404", description = "뉴스가 존재하지 않음",
                content = @Content(mediaType = "text/plain", schema = @Schema(example = "뉴스를 찾을 수 없습니다")))
        }
    )
    @GetMapping("/reading/{uuid}")
    public ResponseEntity<?> newsReading(Authentication authentication, @PathVariable UUID uuid) {
        NewsDto newsDto = newsService.readNews(authentication, uuid);

        if (newsDto==null)
            return new ResponseEntity<>("뉴스를 찾을 수 없습니다", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(newsDto, HttpStatus.OK);
    }

    @Operation(
        summary = "전체 뉴스 목록 조회",
        description = "`sort=latest` 또는 `sort=views` 기준으로 전체 뉴스를 조회.",
        parameters = {
            @Parameter(name = "sort", in = ParameterIn.QUERY, description = "정렬 기준 (latest 또는 views)", required = true),
            @Parameter(name = HttpHeaders.AUTHORIZATION, in = ParameterIn.HEADER,
                description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "뉴스 목록 조회 성공",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))),
            @ApiResponse(responseCode = "400", description = "정렬 기준 오류",
                content = @Content(schema = @Schema(type = "string", example = "정렬 기준을 입력해주세요.")))
        }
    )
    @GetMapping("/reading/all")
    public ResponseEntity<?> readAllNewsByLatest(@RequestParam String sort) {
        if (!"views".equals(sort) && !"latest".equals(sort)) {
            return new ResponseEntity<>("정렬 기준을 입력해주세요.", HttpStatus.BAD_REQUEST);
        }

        List<NewsDto> newsDtos;

        switch (sort.toLowerCase()) {
            case "latest" -> newsDtos = newsService.readAllNewsByLatest();
            case "views" -> newsDtos = newsService.readAllNewsByViews();
            default -> {
                return new ResponseEntity<>("정렬 순서를 입력해주세요", HttpStatus.BAD_REQUEST);
            }
        }

        return  new ResponseEntity<>(newsDtos, HttpStatus.OK);
    }

    @Operation(
        summary = "뉴스 키워드 검색",
        description = "제목에 키워드가 포함된 뉴스를 검색.<br>2자 이상 입력 필요.",
        parameters = {
            @Parameter(name = "keyword", in = ParameterIn.QUERY, description = "검색 키워드", required = true),
            @Parameter(name = HttpHeaders.AUTHORIZATION, in = ParameterIn.HEADER,
                description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "검색 결과 반환",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))),
            @ApiResponse(responseCode = "400", description = "검색어 오류",
                content = @Content(schema = @Schema(type = "string", example = "검색어는 2글자 이상 입력해주세요.")))
        }
    )
    @GetMapping("/searching")
    public ResponseEntity<?> searchNewsByKeyword(@RequestParam String keyword) {
        if (keyword==null || keyword.length() <= 1)
            return new ResponseEntity<>("검색어는 2글자 이상 입력해주세요.", HttpStatus.BAD_REQUEST);

        List<NewsDto> newsDtos = newsService.searchNews(keyword);

        return new ResponseEntity<>(newsDtos, HttpStatus.OK);
    }

    @Operation(
        summary = "북마크 뉴스 키워드 검색",
        description = "로그인된 사용자가 북마크한 뉴스 중 키워드를 포함한 뉴스를 검색.",
        parameters = {
            @Parameter(name = "keyword", in = ParameterIn.QUERY, description = "검색 키워드", required = true),
            @Parameter(name = HttpHeaders.AUTHORIZATION, in = ParameterIn.HEADER,
                description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "검색 결과 반환",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))),
            @ApiResponse(responseCode = "400", description = "검색어 오류",
                content = @Content(schema = @Schema(type = "string", example = "검색어는 2글자 이상 입력해주세요.")))
        }
    )
    @GetMapping("/searching/bookmark")
    public ResponseEntity<?> searchBookmarkByKeyword(Authentication authentication, @RequestParam String keyword) {
        if (keyword==null || keyword.length() <= 1)
            return new ResponseEntity<>("검색어는 2글자 이상 입력해주세요.", HttpStatus.BAD_REQUEST);

        List<NewsDto> newsDtos = newsService.searchBookmark(authentication, keyword);

        return new ResponseEntity<>(newsDtos, HttpStatus.OK);
    }

    @Operation(
        summary = "북마크 여부 확인",
        description = "뉴스 UUID를 통해 북마크 되었는지 여부 확인.",
        parameters = {
            @Parameter(
                in = ParameterIn.PATH,
                name = "uuid",
                description = "뉴스 UUID",
                required = true,
                example = "123e4567-e89b-12d3-a456-426614174000"
            ),
            @Parameter(
                in = ParameterIn.HEADER,
                name = HttpHeaders.AUTHORIZATION,
                required = true,
                description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}"
            )
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "북마크 뉴스 여부 확인",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 사용자",
                content = @Content(mediaType = "text/plain", schema = @Schema(example = "존재하지 않는 사용자 입니다.")))
        }
    )
    @GetMapping("/check-bookmark/{uuid}")
    public ResponseEntity<?> checkBookmark(Authentication authentication, @PathVariable UUID uuid) {
        Boolean isBookmarked = newsService.isBookmarked(authentication, uuid);

        if (isBookmarked==null)
            return new ResponseEntity<>("존재하지 않는 사용자 입니다.", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(isBookmarked, HttpStatus.OK);
    }

    @Operation(
        summary = "북마크 안된 뉴스 조회",
        description = "북마크가 되지 않은 뉴스들을 전부 조회",
        parameters = {
            @Parameter(name = HttpHeaders.AUTHORIZATION, in = ParameterIn.HEADER,
                description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "북마크 되지 않은 뉴스 리스트",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 사용자",
                content = @Content(mediaType = "text/plain", schema = @Schema(example = "존재하지 않는 사용자 입니다.")))
        }
    )
    @GetMapping("/un-bookmark")
    public ResponseEntity<?> unBookmark(Authentication authentication) {
        List<NewsDto> newsDtos = newsService.unBookmarkedNews(authentication);

        if (newsDtos==null)
            return new ResponseEntity<>("존재하지 않는 사용자 입니다.", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(newsDtos, HttpStatus.OK);
    }
}

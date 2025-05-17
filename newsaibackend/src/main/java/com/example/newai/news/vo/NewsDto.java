package com.example.newai.news.vo;

import com.example.newai.domain.vo.DomainDto;
import com.example.newai.word.vo.WordDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    private Long newsId;
    private UUID uuid;

    private String title;
    private String author;
    private List<DomainDto> domains;
    private String content;
    private Integer views;
    private String thumbnailUrl;

    private List<WordDto> words;

    private LocalDateTime createdAt;
}

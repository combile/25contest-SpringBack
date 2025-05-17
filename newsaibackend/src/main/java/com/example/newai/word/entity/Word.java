package com.example.newai.word.entity;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.vo.Level;
import com.example.newai.news.entity.News;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordId;

    @NotNull
    @NotBlank
    private String word;
    @NotNull
    @NotBlank
    private String definition;
    @NotNull
    @NotBlank
    private String sentence;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    @ManyToMany(mappedBy = "seenWords", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},  fetch = FetchType.LAZY)
    private Set<AppUser> appUsers = new HashSet<>();
}

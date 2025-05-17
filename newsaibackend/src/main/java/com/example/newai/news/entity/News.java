package com.example.newai.news.entity;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.comment.entity.Comment;
import com.example.newai.domain.entity.Domain;
import com.example.newai.pnevaluation.entity.PNEvaluation;
import com.example.newai.summary.entity.Summary;
import com.example.newai.word.entity.Word;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(unique = true)
    private UUID uuid;

    @NotNull
    @NotBlank
    private String title;
    private String author;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Domain> domains =  new ArrayList<>();
    private String content;
    private Integer views;

    private String thumbnailUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // LOW MIDDLE HIGH가 존재해서 일대다
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Summary> summaries = new ArrayList<>();
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Word> words = new ArrayList<>();
    @OneToOne(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PNEvaluation pnEvaluation;
    @OneToMany(mappedBy = "news", cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


    @ManyToMany(mappedBy = "bookmarks", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},  fetch = FetchType.LAZY)
    private Set<AppUser> appUsers = new HashSet<>();
}

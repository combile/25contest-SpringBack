package com.example.newai.appuser.entity;

import com.example.newai.appuser.vo.Level;
import com.example.newai.comment.entity.Comment;
import com.example.newai.member.entity.Member;
import com.example.newai.news.entity.News;
import com.example.newai.quiz.repository.QuizRepository;
import com.example.newai.quizresult.entity.QuizResult;
import com.example.newai.word.entity.Word;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class AppUser {
    // AI 계정을 따로 만들어서 나중에 AI Comment 생길시 AI USER랑 연결
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appUserId;

    @NotNull
    @NotBlank
    @Max(value = 5)
    private String username;
    @NotNull
    private String phoneNumber;
    @NotNull
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level vocabularyLevel;

    @CreationTimestamp
    private LocalDateTime createAt;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Member member;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<News> bookmarks = new ArrayList<>();
    // 본 단어는 Word에서 뽑아오기
    // 뉴스를 보게되면 해당 뉴스 안에 있는 단어를 seenWords에 모두 넣어줘야 함.
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Word> seenWords = new ArrayList<>();
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuizResult> quizResults = new ArrayList<>();
}

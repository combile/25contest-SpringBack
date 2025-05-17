package com.example.newai.appuser.entity;

import com.example.newai.appuser.vo.Level;
import com.example.newai.comment.entity.Comment;
import com.example.newai.login.RefreshToken;
import com.example.newai.member.entity.Member;
import com.example.newai.news.entity.News;
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
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String username;
    @NotNull
    @Column(unique = true)
    private String phoneNumber;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level vocabularyLevel;

    private Integer views;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Member member;
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private RefreshToken refreshToken;

    @ManyToMany
    @JoinTable(
            name = "appuser_bookmarks",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "news_id")
    )
    private Set<News> bookmarks = new HashSet<>();
    @ManyToMany
    @JoinTable(
        name = "appuser_seen_words",
        joinColumns = @JoinColumn(name = "app_user_id"),
        inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private Set<Word> seenWords = new HashSet<>();
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuizResult> quizResults = new ArrayList<>();

    public void setMember(Member member) {
        if (member == null) {
            if (this.member != null) {
                this.member.setAppUser(null);
            }
        } else {
            member.setAppUser(this);
        }
        this.member = member;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        if (refreshToken == null) {
            if (this.refreshToken != null) {
                this.refreshToken.setAppUser(null);
            }
        } else {
            refreshToken.setAppUser(this);
        }
        this.refreshToken = refreshToken;
    }

}

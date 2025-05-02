package com.example.newai.news.entity;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.comment.entity.Comment;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @NotNull
    @NotBlank
    private String title;
    private String author;
    private String domain;
    private String content;

    private Integer views;

    private String thumbnailUrl;
    private String topImageUrl;

    @CreationTimestamp
    private LocalDateTime createAt;

    @OneToOne(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // 추후 OneToMany 할것인지 정하면 그때
    private Summary summary;
    @OneToOne(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // 추후 OneToMany 할것인지 정하면 그때
    private PNEvaluation pnEvaluation;
    // wordDefinition 추가할지 말지 결정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
}

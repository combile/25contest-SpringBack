package com.example.newai.pnevaluation.entity;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.news.entity.News;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class PNEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pnEvaluationId;

    @NotNull
    @NotBlank
    private String positiveComment;
    @NotNull
    @NotBlank
    private String negativeComment;

    @OneToOne(fetch = FetchType.LAZY) // 추후 Many 변경 가능
    @JoinColumn(name = "news_id")
    private News news;
}


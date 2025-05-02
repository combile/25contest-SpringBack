package com.example.newai.summary.entity;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.vo.Level;
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
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long summaryId;

    private String type;
    @Enumerated(EnumType.STRING)
    private Level level;
    @NotNull
    @NotBlank
    private String summaryContent;

    @OneToOne(fetch = FetchType.LAZY) // 추후 Many 변경 가능
    @JoinColumn(name = "news_id")
    private News news;
}

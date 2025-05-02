package com.example.newai.quiz.entity;

import com.example.newai.quizresult.entity.QuizResult;
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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;

    @NotNull
    @NotBlank
    private String question;
    @NotNull
    @NotBlank
    private Integer answer;

    @OneToOne(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private QuizResult quizResult;
}

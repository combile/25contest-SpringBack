package com.example.newai.quizresult.entity;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.quiz.entity.Quiz;
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
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizResultId;

    @NotNull
    @NotBlank
    private Integer selectedValue;
    @NotNull
    @NotBlank
    private boolean isCorrect;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
}

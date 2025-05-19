package com.example.newai.quiz.vo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private Long quizId;

    private String question;

    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFore;
}

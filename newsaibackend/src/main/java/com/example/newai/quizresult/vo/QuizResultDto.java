package com.example.newai.quizresult.vo;


import com.example.newai.quiz.vo.QuizDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultDto {
    private Long quizResultId;
    private QuizDto quizDto;

    private Integer selectedValue;
    private boolean isCorrect;
}

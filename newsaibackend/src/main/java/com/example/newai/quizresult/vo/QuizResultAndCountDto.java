package com.example.newai.quizresult.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultAndCountDto {
    private List<QuizResultDto> quizResultDtoList;
    private Integer collectCount;
}

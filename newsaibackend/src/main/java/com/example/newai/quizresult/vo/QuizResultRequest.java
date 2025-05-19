package com.example.newai.quizresult.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuizResultRequest {
    private List<QuizIdAndSelectValue> quizIdAndSelectValueList;
}

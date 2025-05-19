package com.example.newai.quizresult.service;

import com.example.newai.quizresult.vo.QuizResultAndCountDto;
import com.example.newai.quizresult.vo.QuizResultDto;
import com.example.newai.quizresult.vo.QuizResultRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface QuizResultService {
    QuizResultAndCountDto gradingQuiz(Authentication authentication, QuizResultRequest quizResultRequest);
    List<QuizResultDto> myQuizResults(Authentication authentication);
    QuizResultDto readQuizResult(Long quizResultId);
}

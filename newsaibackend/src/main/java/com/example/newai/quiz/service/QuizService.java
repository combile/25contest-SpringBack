package com.example.newai.quiz.service;

import com.example.newai.quiz.vo.QuizDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface QuizService {
    List<QuizDto> extractQuiz(Authentication authentication);
    QuizDto readQuizById(Long quizId);
}

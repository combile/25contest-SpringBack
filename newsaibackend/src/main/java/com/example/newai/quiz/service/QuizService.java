package com.example.newai.quiz.service;

import com.example.newai.quiz.vo.QuizDto;

import java.util.List;

public interface QuizService {
    List<QuizDto> extractQuiz();
    QuizDto readQuizById(Long quizId);
}

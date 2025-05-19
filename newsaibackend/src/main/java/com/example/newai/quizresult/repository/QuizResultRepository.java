package com.example.newai.quizresult.repository;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.quiz.entity.Quiz;
import com.example.newai.quizresult.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult,Long> {
    Optional<QuizResult> findByQuizAndAppUser(Quiz quiz, AppUser appUser);
    List<QuizResult> findByAppUser(AppUser appUser);
}

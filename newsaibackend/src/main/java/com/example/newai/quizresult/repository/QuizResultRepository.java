package com.example.newai.quizresult.repository;

import com.example.newai.quizresult.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult,Long> {
}

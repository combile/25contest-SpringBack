package com.example.newai.pnevaluation.repository;

import com.example.newai.news.entity.News;
import com.example.newai.pnevaluation.entity.PNEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PNEvaluationRepository extends JpaRepository<PNEvaluation, Long> {
    PNEvaluation findByNews(News news);
}

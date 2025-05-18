package com.example.newai.pnevaluation.service;

import com.example.newai.news.entity.News;
import com.example.newai.news.repository.NewsRepository;
import com.example.newai.pnevaluation.entity.PNEvaluation;
import com.example.newai.pnevaluation.repository.PNEvaluationRepository;
import com.example.newai.pnevaluation.vo.PNEvaluationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PNEvaluationServiceJpaImpl implements PNEvaluationService {
    private final NewsRepository newsRepository;
    private final PNEvaluationRepository pnevaluationRepository;

    @Override
    public PNEvaluationDto readPNEvaluation(UUID uuid) {
        News news = newsRepository.findByUuid(uuid);

        if (news==null)
            return null;

        PNEvaluation pnevaluation = pnevaluationRepository.findByNews(news);

        if (pnevaluation==null)
            return null;

        return pnevaluationToPNEvaluationDto(pnevaluation);
    }

    public static PNEvaluationDto pnevaluationToPNEvaluationDto(PNEvaluation pnevaluation) {
        return new PNEvaluationDto(pnevaluation.getPnEvaluationId(), pnevaluation.getPositiveComment(), pnevaluation.getNegativeComment());
    }
}

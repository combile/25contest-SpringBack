package com.example.newai.pnevaluation.service;

import com.example.newai.pnevaluation.vo.PNEvaluationDto;

import java.util.UUID;

public interface PNEvaluationService {
    PNEvaluationDto readPNEvaluation(UUID uuid);
}

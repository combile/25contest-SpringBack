package com.example.newai.summary.service;

import com.example.newai.summary.vo.SummaryDto;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface SummaryService {
    SummaryDto readSummary(Authentication authentication, UUID uuid);
}
